package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.model.Evento;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.AgendaListHandler;
import br.com.brosource.hstgbrasil.util.C;


public class InicialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        HstgRestClient.getAgendaList(new AgendaListHandler() {
            @Override
            public void onSuccess(ArrayList<Evento> list) {
                // faz o que tiver que fazer com os objetos retornados
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
