package com.koto.mykoto.common;

public interface MetaCallback <T, M> {
    void onSuccess(T entity, M meta);
    void onError(ApiError error);
}
