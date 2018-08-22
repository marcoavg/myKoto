package com.koto.mykoto.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koto.mykoto.MyApp;
import com.koto.mykoto.models.Session;
import com.koto.mykoto.models.Users;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CurrentUser {

    private static final String CURRENT_SESSION = "currentSession";
    private static final String CURRENT_USER = "currentUser";
    private static final String PREF_GCM_REG_ID = "registration_id";
    private static final String CURRENT_NOTIFICATIONS = "notifications";

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static SharedPreferences sharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(MyApp.getInstance());
    }

    public static void setCurrentSession(Session session) {
        String jsonSession;
        if (session == null)
            jsonSession = "";
        else
            jsonSession = new Gson().toJson(session);
        saveString(CURRENT_SESSION, jsonSession);
    }

    public static void deleteCurrentSession() {
        String jsonSession = "";
        saveString(CURRENT_SESSION, jsonSession);
    }
    public static Session getCurrentSession() {
        String jsonSession = sharedPreferences().getString(CURRENT_SESSION, "");
        if (jsonSession.isEmpty())
            return null;
        return new Gson().fromJson(jsonSession, Session.class);
    }

    public static void setCurrentUser(Users user) {
        String json;
        if (user == null)
            json = "";
        else
            json = new Gson().toJson(user);
        saveString(CURRENT_USER, json);
    }

    public static Users getCurrentUser() {
        String json = sharedPreferences().getString(CURRENT_USER, "");
        if (json.isEmpty())
            return null;
        return new Gson().fromJson(json, Users.class);
    }



    public static void deleteNotifications() {
        String jsonSession = "";
        saveString(CURRENT_NOTIFICATIONS, jsonSession);
    }

    public static void setGCMId(String gcmId){
        saveString(PREF_GCM_REG_ID, gcmId);
    }

    public static String getGCMId() {
        return sharedPreferences().getString(PREF_GCM_REG_ID, "");
    }
}
