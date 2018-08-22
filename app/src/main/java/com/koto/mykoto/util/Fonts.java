package com.koto.mykoto.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fonts {

    public static void changeFonts(Context context, View view) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    changeFonts(context, child);
                }
            } else if (view instanceof TextView) {
                TextView textView = (TextView) view;
                String fontName = "fonts/oxygen_regular.ttf"; //As default the font is regular
                if (textView.getTag() != null) { //use tags on the views to change bold - italic
                    if (textView.getTag().equals("bold"))
                        fontName = "fonts/oxygen_bold.ttf";
                    else if (textView.getTag().equals("light"))
                        fontName = "fonts/oxygen_light.ttf";
                }

                Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
                textView.setTypeface(typeface);
            }
            if (view instanceof TextInputLayout) {
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/oxygen_regular.ttf");
                ((TextInputLayout) view).setTypeface(typeface);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}