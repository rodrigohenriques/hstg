package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.gui.adapter.InstagramPictureAdapter;
import br.com.brosource.hstgbrasil.gui.adapter.NewsAdapter;
import br.com.brosource.hstgbrasil.model.InstagramPicture;
import br.com.brosource.hstgbrasil.model.Produto;
import br.com.brosource.hstgbrasil.server.InstagramClient;
import br.com.brosource.hstgbrasil.server.handler.InstagramPictureListHandler;
import br.com.brosource.hstgbrasil.util.C;
import br.com.brosource.hstgbrasil.util.Instagram;
import br.com.brosource.hstgbrasil.util.Prefs;
import br.com.brosource.hstgbrasil.widgets.ButteryProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GaleriaActivity extends Activity {
    Prefs prefs;

    @InjectView(R.id.galeria_grid)
    GridView gridView;

    ButteryProgressBar progressBar;

    InstagramPictureAdapter instagramPictureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        ButterKnife.inject(this);

        progressBar = new ButteryProgressBar(this);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 24));

        final FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        decorView.addView(progressBar);

        ViewTreeObserver observer = progressBar.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View contentView = decorView.findViewById(android.R.id.content);
                progressBar.setY(contentView.getY());

                ViewTreeObserver observer = progressBar.getViewTreeObserver();
                observer.removeGlobalOnLayoutListener(this);
            }
        });

        Uri data = getIntent().getData();

        if (data != null) {
            String token = data.toString().split(Instagram.PARAM_ACCESS_TOKEN + "=")[1];

            prefs = new Prefs(this);

            prefs.put(Prefs.Keys.INSTAGRAM_TOKEN, token);

            InstagramClient.searchPostByHashtag(token, C.App.HASHTAG, new InstagramPictureListHandler() {
                @Override
                public void onSuccess(ArrayList<InstagramPicture> list) {
                    Log.e(C.App.LOG_TAG, list.toString());

                    progressBar.setVisibility(View.INVISIBLE);

                    instagramPictureAdapter = new InstagramPictureAdapter(GaleriaActivity.this, list);
                    gridView.setAdapter(instagramPictureAdapter);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_galeria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        finish();
    }

    @OnClick(R.id.btn_back)
    public void back() {
        onBackPressed();
    }
}
