package br.com.brosource.hstgbrasil.model;

import android.graphics.Color;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Galeria")
public class Gallery extends ParseObject {

    public String getHashtag() {
        return getString("hashtag");
    }

    public String getHashtagFormatted() {
        return String.format("#%s", getString("hashtag"));
    }

    public String getHexColor() {
        return getString("hexColor");
    }

    public int getIntColor() {
        return Color.parseColor(getHexColor());
    }

    public static ParseQuery<Gallery> query() {
        return ParseQuery.getQuery(Gallery.class);
    }
}
