package com.koto.mykoto.common;

public class ApiError {
    private int code;
    private String message;
    private String localizedMessage;


    public ApiError(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }
}
