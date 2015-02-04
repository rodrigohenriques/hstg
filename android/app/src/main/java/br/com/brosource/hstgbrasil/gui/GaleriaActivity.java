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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.gui.adapter.InstagramPictureAdapter;
import br.com.brosource.hstgbrasil.model.InstagramPicture;
import br.com.brosource.hstgbrasil.server.InstagramClient;
import br.com.brosource.hstgbrasil.server.handler.InstagramPictureListHandler;
import br.com.brosource.hstgbrasil.util.C;
import br.com.brosource.hstgbrasil.util.CustomFont;
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

    @InjectView(R.id.btn_back)
    ImageView btnBack;
    @InjectView(R.id.txt_galeria)
    TextView labelGaleria;
    @InjectView(R.id.txt_topo)
    TextView btnTopo;

    ButteryProgressBar progressBar;

    InstagramPictureAdapter instagramPictureAdapter;
    ArrayList<InstagramPicture> instagramPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        ButterKnife.inject(this);

        labelGaleria.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        btnTopo.setTypeface(CustomFont.getHumeGeometricSans3Light(this));

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

        prefs = new Prefs(this);

        Uri data = getIntent().getData();

        if (data != null) {
            String[] info = data.toString().split(Instagram.PARAM_ACCESS_TOKEN + "=");

            try {
                if (info != null && info[1] != null) {

                    String token = info[1];

                    prefs.put(Prefs.Keys.INSTAGRAM_TOKEN, token);
                } else {
                    finish();
                }
            } catch(Exception e) {
                finish();
            }
        }

        InstagramClient.searchPostByHashtag(prefs.get(Prefs.Keys.INSTAGRAM_TOKEN), C.App.HASHTAG, new InstagramPictureListHandler() {
            @Override
            public void onSuccess(ArrayList<InstagramPicture> list) {
                Log.e(C.App.LOG_TAG, list.toString());

                instagramPictures = new ArrayList<InstagramPicture>(list);
                progressBar.setVisibility(View.INVISIBLE);

                instagramPictureAdapter = new InstagramPictureAdapter(GaleriaActivity.this, list);
                gridView.setAdapter(instagramPictureAdapter);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent itt = new Intent(GaleriaActivity.this, InstagramPagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list_instagram", instagramPictures);
                bundle.putInt("position", position);

                itt.putExtras(bundle);

                startActivity(itt);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_galeria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        finish();
    }

    @OnClick(R.id.btn_back)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.txt_topo)
    public void toTop() {
        gridView.setSelection(0);
    }
}
