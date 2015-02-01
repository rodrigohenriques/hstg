package br.com.brosource.hstgbrasil.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rodrigohenriques on 11/20/14.
 */
public class Prefs {
    private final String NAME = "hstg.pref";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public enum Keys {
        INSTAGRAM_TOKEN,
        USERNAME,
        EMAIL,
        BIRTHDAY,
        CEP,
        CIDADE,
        ESTADO,
        LOCATION, PHONE
    }

    public Prefs(Context context) {
        pref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public String get(Keys key) {
        return pref.getString(key.toString(), null);
    }

    public void put(Keys key, String value) {
        pref.edit().putString(key.toString(), value).apply();
    }
 }
