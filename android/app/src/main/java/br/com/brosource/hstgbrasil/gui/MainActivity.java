package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.gui.adapter.MainAdapter;
import br.com.brosource.hstgbrasil.model.Noticia;
import br.com.brosource.hstgbrasil.server.GraphClient;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.NewsListHandler;
import br.com.brosource.hstgbrasil.util.C;
import br.com.brosource.hstgbrasil.util.CustomFont;
import br.com.brosource.hstgbrasil.util.HstgUtil;
import br.com.brosource.hstgbrasil.util.Instagram;
import br.com.brosource.hstgbrasil.util.Prefs;
import br.com.brosource.hstgbrasil.widgets.ImageViewCircle;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @InjectView(R.id.main_news)
    TextView mNews;
    @InjectView(R.id.main_agenda)
    TextView mAgenda;
    @InjectView(R.id.main_galeria)
    TextView mGaleria;
    @InjectView(R.id.main_produtos)
    TextView mProdutos;
    @InjectView(R.id.main_meus_dados)
    TextView mDados;
    @InjectView(R.id.main_wifi_party)
    TextView mWiFi;

    Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        prefs = new Prefs(this);

        mNews.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mAgenda.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mGaleria.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mProdutos.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mDados.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mWiFi.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
    }

    @OnClick(R.id.main_news)
    public void openNews() {
        Intent itt = new Intent(this, NewsActivity.class);
        startActivity(itt);
    }

    @OnClick(R.id.main_agenda)
    public void openAgenda() {
        Intent itt = new Intent(MainActivity.this, AgendaActivity.class);
        startActivity(itt);
    }

    @OnClick(R.id.main_galeria)
    public void openGaleria() {
        if (prefs.get(Prefs.Keys.INSTAGRAM_TOKEN) == null) {

            Instagram.oAuth(MainActivity.this);
        } else {

            Intent itt = new Intent(MainActivity.this, GaleriaActivity.class);
            startActivity(itt);
        }
    }

    @OnClick(R.id.main_produtos)
    public void openProdutos() {
        Intent itt = new Intent(MainActivity.this, ProdutoActivity.class);
        startActivity(itt);
    }

    @Override
    public void onBackPressed() {

    }

    @OnClick(R.id.main_meus_dados)
    public void openMeusDados() {
        Intent itt = new Intent(MainActivity.this, MeusDadosActivity.class);
        startActivity(itt);
    }
}