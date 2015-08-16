package br.com.brosource.hstgbrasil.server.handler;

import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.model.Evento;
import br.com.brosource.hstgbrasil.util.Constants;

/**
 * Created by rodrigohenriques on 11/9/14.
 */
public abstract class AgendaListHandler extends TextHttpResponseHandler {
    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.e(Constants.App.LOG_TAG, "Erro de conversao de String para JsonArray", throwable);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.i(Constants.App.LOG_TAG, responseString);

        try {
            ArrayList<Evento> list = new ArrayList<Evento>();
            JSONArray array = new JSONArray(responseString);

            for (int i = 0; i < array.length(); i++) {
                Evento evento = Evento.load(array.getString(i));

                list.add(evento);

                Log.i(Constants.App.LOG_TAG, evento.toString());
            }

            onSuccess(list);
        } catch (JSONException e) {
            Log.e(Constants.App.LOG_TAG, "Erro de conversao de String para JsonArray", e);
        }
    }

    public abstract void onSuccess(ArrayList<Evento> list);
}
