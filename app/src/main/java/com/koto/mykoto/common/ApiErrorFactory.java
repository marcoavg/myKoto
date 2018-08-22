package com.koto.mykoto.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koto.mykoto.MyApp;
import com.koto.mykoto.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ApiErrorFactory {
    public static ApiError fromResponse(Response<?> response) {
        int code = response.code();
        String message = "";
        try {
            String body = response.errorBody().string();
            Map<String, Object> retMap = new Gson().fromJson(
                    body, new TypeToken<HashMap<String, Object>>() {}.getType()
            );
            List<String> strings = new ArrayList<>();
            for (Object o: retMap.values()){
                try {
                    Object[] arrayList = ((ArrayList) o).toArray();
                    for (Object item : arrayList) {
                        strings.add(capitalizeFirstLetter(item.toString().replace(", ", ",")
                                .replace("{", "")
                                .replace("}", "")
                                .replace("source=pointer=/data/attributes", "")
                                .replace("[", "")
                                .replace("]", "")
                                .replace("=", ": ")
                                .replace("/", "")
                                .replace(",", "")
                                .replace("detail", "")));
                    }
                }catch (Exception e){
                    strings.add(capitalizeFirstLetter(o.toString().replace(", ", ",")
                            .replace("{", "")
                            .replace("}", "")
                            .replace("source=pointer=/data/attributes", "")
                            .replace("[", "")
                            .replace("]", "")
                            .replace("=", ": ")
                            .replace(" ", " ")
                            .replace(",", "")
                            .replace("detail", "")));
                }
            }
            for (String s : strings)
                message += s + " \n ";

        } catch (IOException e) {
            e.printStackTrace();
        }
        ApiError error = new ApiError(message);
        error.setCode(code);
        return error;
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }


    public static ApiError fromThrowable(Throwable t) {
        String message = MyApp.getInstance().getString(R.string.error_server_error);
        return new ApiError(message);
    }
    private static String jsonArrayToString(JSONArray jsonArray) {
        String str = "";
        if (jsonArray==null) return str;
        if (jsonArray.length()<0) return str;

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                str = str + " " + jsonArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}