package com.koto.mykoto.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.koto.mykoto.R;
import com.koto.mykoto.dialogs.ProgressDialog;
import com.koto.mykoto.hellpers.NetworkHelper;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void showProgress() {
        progressDialog.showProgressDialog();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismissProgressDialog();
    }



    @Override
    public void showSnackbar(String message) {
        snackBar("", message);
    }

    @Override
    public boolean isConnect(){
        if(!NetworkHelper.isOnline(this)) {
            snackBar("CatVideoLive", getString(R.string.no_network));
            return false;
        }else
            return true;
    }

    private void snackBar(String textButton, String message){
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        Snackbar snackbar = Snackbar.make(viewGroup, textButton, Snackbar.LENGTH_LONG)
                .setAction(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.setActionTextColor(getColor(R.color.white));
        View snackBarView = snackbar.getView();
        snackBarView.setOnClickListener(v-> snackbar.dismiss());
        snackBarView.setBackground(this.getDrawable(R.drawable.ic_launcher_background));
        Button button = snackBarView.findViewById(android.support.design.R.id.snackbar_action);
        button.setAllCaps(false);
        TextView tv = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(getColor(R.color.white));
        snackbar.show();

    }

    @Override
    public String addFragment(Fragment fragment) {
        return "";
    }


    @Override
    public void fragmentReturn(){}
}