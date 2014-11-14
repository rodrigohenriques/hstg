package br.com.brosource.hstgbrasil.server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.brosource.hstgbrasil.util.C;

/**
 * Created by rodrigohenriques on 11/13/14.
 */
public class GraphClient {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getPhotoFacebook(final String id, final ImageView imageView) {

        File f = new File(C.App.Files.PROFILE_PIC);

        f.getParentFile().mkdirs();

        client.get("https://graph.facebook.com/"+id+"/picture?type=large", new FileAsyncHttpResponseHandler(f) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Log.e(C.App.LOG_TAG, "Carregamento da imagem do perfil", throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {

                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                    imageView.setImageBitmap(bitmap);


                } catch (FileNotFoundException e) {
                    Log.e(C.App.LOG_TAG, "Carregamento da imagem do perfil", e);
                }
            }
        });
    }
}
