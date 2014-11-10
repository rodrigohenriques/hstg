package br.com.brosource.hstgbrasil.server.handler;

import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import br.com.brosource.hstgbrasil.model.Evento;
import br.com.brosource.hstgbrasil.util.C;

/**
 * Created by rodrigohenriques on 11/9/14.
 */
public class AgendaListHandler extends TextHttpResponseHandler {
    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.i(C.App.LOG_TAG, responseString);

        try {
            JSONArray array = new JSONArray(responseString);

            for (int i = 0; i < array.length(); i++) {

                Evento evento = Evento.load(array.getString(i));

                Log.i(C.App.LOG_TAG, evento.toString());
            }
        } catch (JSONException e) {
            Log.e(C.App.LOG_TAG, "Erro de conversao de String para JsonArray", e);
        }
    }
}
