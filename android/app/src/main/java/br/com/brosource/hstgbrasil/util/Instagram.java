package br.com.brosource.hstgbrasil.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by rodrigohenriques on 11/20/14.
 */
public class Instagram {
    private final static String CLIENT_ID = "d7fddae81b9c4f8ea35323861c752c18";
    private final static String REDIRECT_URI = "hstg://insta-auth";
    private final static String IMPLICIT_AUTH_URL = "https://instagram.com/oauth/authorize/?client_id=%s&redirect_uri=%s&response_type=token";
    public static final String PARAM_ACCESS_TOKEN = "access_token";

    public static void oAuth(Context context) {
        String url = String.format(IMPLICIT_AUTH_URL, CLIENT_ID, REDIRECT_URI);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
}
