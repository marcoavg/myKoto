package com.koto.mykoto.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.koto.mykoto.R;

public class ProgressDialog extends Dialog {

    public ProgressDialog(final Context context) {
        super(context, R.style.AppTheme_Dialog);
        this.setContentView(R.layout.dialog_progress);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        final Animation animation = AnimationUtils.loadAnimation(context,
                R.anim.rotate);
        final ImageView imageView = findViewById(R.id.image_view_load);
        imageView.setAnimation(animation);
    }

    public void dismissProgressDialog() {
        if (isShowing()) {
            dismiss();
        }
    }

    public void showProgressDialog() {
        if (!isShowing()) {
            show();
        }
    }
}
