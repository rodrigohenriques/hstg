package br.com.brosource.hstgbrasil.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by rodrigohenriques on 11/11/14.
 */
public class CustomFont {
    public static Typeface getHumeGeometricSans3Thin(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/HurmeGeometricSans3-Thin.otf");
    }

    public static Typeface getHumeGeometricSans3Light(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/HurmeGeometricSans3-Light.otf");
    }

    public static Typeface getHumeGeometricSans3Bold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/HurmeGeometricSans3-Bold.otf");
    }
}
