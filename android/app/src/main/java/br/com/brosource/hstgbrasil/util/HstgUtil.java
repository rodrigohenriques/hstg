package br.com.brosource.hstgbrasil.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rodrigohenriques on 11/9/14.
 */
public class HstgUtil {
    public static final Gson GSON = new Gson();

    static {

    }

    public Bitmap getPhotoFacebook(final String id) {

        Bitmap bitmap = null;
        final String nomimg = "https://graph.facebook.com/"+id+"/picture?type=large";
        URL imageURL = null;

        try {
            imageURL = new URL(nomimg);

            try {
                HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects( true );
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                //img_value.openConnection().setInstanceFollowRedirects(true).getInputStream()
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                Log.e(C.App.LOG_TAG, "Carregamento da imagem do perfil", e);
            }
        } catch (MalformedURLException e) {
            Log.e(C.App.LOG_TAG, "URL mal formada.\n" + nomimg, e);
        }


        return bitmap;

    }
}
