package br.com.brosource.hstgbrasil.server.handler;

import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.model.Evento;
import br.com.brosource.hstgbrasil.model.Noticia;
import br.com.brosource.hstgbrasil.util.C;

/**
 * Created by rodrigohenriques on 11/9/14.
 */
public abstract class NewsListHandler extends TextHttpResponseHandler {
    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.e(C.App.LOG_TAG, "Erro de conversao de String para JsonArray", throwable);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.i(C.App.LOG_TAG, responseString);

        try {
            ArrayList<Noticia> list = new ArrayList<Noticia>();
            JSONArray array = new JSONArray(responseString);

            for (int i = 0; i < array.length(); i++) {
                Noticia noticia = Noticia.load(array.getString(i));

                list.add(noticia);

                Log.i(C.App.LOG_TAG, noticia.toString());
            }

            onSuccess(list);
        } catch (JSONException e) {
            Log.e(C.App.LOG_TAG, "Erro de conversao de String para JsonArray", e);
        }
    }

    public abstract void onSuccess(ArrayList<Noticia> list);
}
