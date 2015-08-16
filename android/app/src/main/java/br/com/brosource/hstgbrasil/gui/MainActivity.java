package br.com.brosource.hstgbrasil.gui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.desmond.squarecamera.CameraActivity;
import com.facebook.Session;
import com.facebook.SessionState;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.model.Gallery;
import br.com.brosource.hstgbrasil.util.Constants;
import br.com.brosource.hstgbrasil.util.CustomFont;
import br.com.brosource.hstgbrasil.util.Prefs;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends HstgActivity {

    @InjectView(R.id.main_news)
    TextView mNews;
    @InjectView(R.id.main_agenda)
    TextView mAgenda;
    @InjectView(R.id.main_galerias)
    TextView mGaleria;
    @InjectView(R.id.main_produtos)
    TextView mProdutos;
    @InjectView(R.id.main_meus_dados)
    TextView mDados;

    Prefs prefs;

    final int REQUEST_CAMERA = 9000;

    @Override
    public void onSessionStateChange(Session session, SessionState state, Exception exception) {

    }

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

    @OnClick(R.id.main_galerias)
    public void openGaleria() {

        Intent itt = new Intent(MainActivity.this, GalleriesActivity.class);
        startActivity(itt);


//        if (mPrefs.get(Prefs.Keys.INSTAGRAM_TOKEN) == null) {
//
//            Instagram.oAuth(MainActivity.this);
//        } else {
//
//            Intent itt = new Intent(MainActivity.this, GaleriaActivity.class);
//            startActivity(itt);
//        }
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

    @OnClick(R.id.main_hashtag_party)
    public void openHashtagParty() {
        // Start CameraActivity
        Intent startCustomCameraIntent = new Intent(this, CameraActivity.class);
        startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
    }

    // Receive Uri of saved square photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == REQUEST_CAMERA) {
            final Uri photoUri = data.getData();

            startProgress();

            ParseQuery<Gallery> query = Gallery.query();
            query.whereEqualTo("isParty", true);
            query.getFirstInBackground(new GetCallback<Gallery>() {
                @Override
                public void done(Gallery gallery, ParseException e) {
                    if (e == null) {
                        gallery.pinInBackground();

                        Intent intent = new Intent(MainActivity.this, HashtagPartyOverlayActivity.class);

                        intent.putExtra(Constants.Extras.PHOTO_URI, photoUri);
                        intent.putExtra(Constants.Extras.GALLERY_ID, gallery.getObjectId());

                        startActivity(intent);
                    } else {
                        Log.e(Constants.App.LOG_TAG, e.getMessage(), e);
                        communicateFailure();
                    }

                    finishProgress();
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}