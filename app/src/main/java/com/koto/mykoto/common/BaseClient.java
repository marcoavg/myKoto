package com.koto.mykoto.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koto.mykoto.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseClient {
    private static final String USER_AUTH_HEADER = "X-User-Email";
    private static final String TOKEN_AUTH_HEADER = "Authorization";

    private static User getUser() {
        return CurrentUser.getCurrentUser();
    }

    @NonNull
    protected static String getEmail() {
        User user = getUser();
        if (user == null) return "";
        if (user.getEmail() == null) return "";
        return user.getEmail();
    }

    @NonNull
    protected static String getToken() {
        User user = getUser();
        if (user == null) return "";
        if (user.getToken() == null) return "";
        return user.getToken();
    }

    @Nullable
    protected static MultipartBody.Part toPartBody(File file, String param) {
        if (file == null)
            return null;
        RequestBody requestBank = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                file
        );
        return MultipartBody.Part.createFormData(
                param,
                file.getName(),
                requestBank
        );
    }

    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override public boolean shouldSkipField (FieldAttributes f) {
                        return f.getAnnotation(SkipSerialization.class) != null;
                    }

                    @Override public boolean shouldSkipClass (Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        OkHttpClient client = getAuthClient();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    private static OkHttpClient getAuthClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    String email = getEmail();
                    String token = getToken();
                    if (email.isEmpty() || token.isEmpty()) {
                        return chain.proceed(chain.request());
                    }
                    Request authorisedRequest = chain.request().newBuilder()
                            .addHeader(USER_AUTH_HEADER, email)
                            .addHeader(TOKEN_AUTH_HEADER, token)
                            .build();

                    return chain.proceed(authorisedRequest);
                })
                .build();
        return okHttpClient;
    }
}

