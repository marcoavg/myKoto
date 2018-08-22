package com.koto.mykoto;

import android.app.Application;
import android.content.Intent;

import com.koto.mykoto.common.ApiError;

public class MyApp extends Application {
    private static final String TAG = "MyApp";
    private static MyApp instance;
    protected String userAgent;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /*public void logOut() {
        LoginClient loginClient = new LoginClient();
        loginClient.signOut(new EntityCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean entity) {
                CurrentUser.deleteCurrentSession();
                CurrentUser.deleteNotifications();
                Session s = new Session();
                s.setToken("guest");
                s.setEmail("guest");
                s.setUserName("guest");
                s.setShowSplash("f");
                s.setShowRegister("t");
                CurrentUser.setCurrentSession(s);
                Intent i = new Intent(MyApp.getInstance(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            @Override
            public void onError(ApiError error) {
                OptionDialog optionDialog = new OptionDialog();
                optionDialog.setTitle(getString(R.string.app_name));
                optionDialog.setMessage(error.getLocalizedMessage());
                new TwoButtonDialog(MyApp.getInstance(), optionDialog, text -> {
                }).show();
            }
        });

    }*/


    public boolean useExtensionRenderers() {
        return BuildConfig.FLAVOR.equals("withExtensions");
    }
}
