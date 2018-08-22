package com.koto.mykoto.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Session implements Parcelable {
    private String email;
    private String token;
    private String userName;
    private String deviceToken;
    private String showSplash;
    private String showRegister;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getShowSplash() {
        return showSplash;
    }

    public void setShowSplash(String showSplash) {
        this.showSplash = showSplash;
    }

    public String getShowRegister() {
        return showRegister;
    }

    public void setShowRegister(String showRegister) {
        this.showRegister = showRegister;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.token);
        dest.writeString(this.userName);
        dest.writeString(this.deviceToken);
        dest.writeString(this.showSplash);
        dest.writeString(this.showRegister);
    }

    public Session() {
    }

    protected Session(Parcel in) {
        this.email = in.readString();
        this.token = in.readString();
        this.userName = in.readString();
        this.deviceToken = in.readString();
        this.showSplash = in.readString();
        this.showRegister = in.readString();
    }

    public static final Parcelable.Creator<Session> CREATOR = new Parcelable.Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel source) {
            return new Session(source);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };
}
