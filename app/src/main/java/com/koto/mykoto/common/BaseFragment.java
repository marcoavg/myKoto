package com.koto.mykoto.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.koto.mykoto.R;
import com.koto.mykoto.dialogs.ProgressDialog;
import com.koto.mykoto.hellpers.NetworkHelper;
import com.koto.mykoto.util.Fonts;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class BaseFragment extends Fragment implements BaseView {
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        fragmentManager = getFragmentManager();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fonts.changeFonts(getActivity(), view);
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
    public void showError(String message) {
        hideProgress();
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
    @Override
    public void fragmentReturn(){
        closeInput();
        assert getFragmentManager() != null;
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        }
    }

    public void closeInput(){
        InputMethodManager input = (InputMethodManager) Objects.requireNonNull(getActivity())
                .getSystemService(INPUT_METHOD_SERVICE);
        try {
            if(input != null)
                input.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }catch (NullPointerException npe){}
    }

    @Override
    public String addFragment(Fragment fragment) {
        closeInput();
        fragmentManager.beginTransaction()
                //.add(R.id.main_content, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName())
                .commit();

        return fragment.getTag();
    }

    @Override
    public void showSnackbar(String message) {
        snackBar("SIAPA", message);
    }

    @Override
    public boolean isConnect(){
        if(!NetworkHelper.isOnline(getContext())) {
            snackBar("SIAPA", getString(R.string.no_network));
            return false;
        }else
            return true;
    }

    private void snackBar(String textButton, String message){
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) getActivity()
                .findViewById(android.R.id.content)).getChildAt(0);
        Snackbar snackbar = Snackbar.make(viewGroup, textButton, Snackbar.LENGTH_LONG)
                .setAction(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.setActionTextColor(getActivity().getColor(R.color.white));
        View snackBarView = snackbar.getView();
        snackBarView.setBackground(getActivity().getDrawable(R.drawable.ic_launcher_background));
        Button button = snackBarView.findViewById(android.support.design.R.id.snackbar_action);
        button.setAllCaps(false);
        TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(getActivity().getColor(R.color.white));
        //Font.OxygenLight.applyTextView(tv,button);
        snackbar.show();
    }

    public void showError(int messageRes) {
        showError(getString(messageRes));
    }
}