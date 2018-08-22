package com.koto.mykoto.common;

import android.support.v4.app.Fragment;

public interface BaseView {
    void showProgress();
    void hideProgress();
    void showError(String message);
    void fragmentReturn();
    String addFragment(Fragment fragment);
    void showSnackbar(String message);
    boolean isConnect();

}
