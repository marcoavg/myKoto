package com.koto.mykoto.common;

public interface EntityCallback<T> {
    void onSuccess(T entity);
    void onError(ApiError error);
}
