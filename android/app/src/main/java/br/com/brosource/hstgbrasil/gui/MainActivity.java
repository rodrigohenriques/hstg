package br.com.brosource.hstgbrasil.gui;

import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import br.com.brosource.hstgbrasil.model.Noticia;
import br.com.brosource.hstgbrasil.server.GraphClient;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.NewsListHandler;
import br.com.brosource.hstgbrasil.util.C;
import br.com.brosource.hstgbrasil.util.CustomFont;
import br.com.brosource.hstgbrasil.util.HstgUtil;
import br.com.brosource.hstgbrasil.widgets.ImageViewCircle;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends HstgActivity {

    @InjectView(R.id.main_profile_pic)
    ImageViewCircle mProfilePic;
    @InjectView(R.id.main_text_saudacao)
    TextView mTxtSaudacao;
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


    @Override
    public void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            // Carrega imagem e saudacao
            if (session != null && session.isOpened()) {
                // Get the user's data.
                makeMeRequest(session);
            }
        } else {
            // TODO retorna pra tela de login do aplicativo
            HstgUtil.logout(this);
        }
    }

    private void makeMeRequest(final Session session) {
        // Make an API call to get user data and define a
        // new callback to handle the response.
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {

                                mProfilePic.invalidate();
                                GraphClient.getPhotoFacebook(user.getId(), mProfilePic);
                                //mProfilePic.setProfileId(user.getId());
                                mTxtSaudacao.setText("Olá, " + user.getName());
                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        mTxtSaudacao.setTypeface(CustomFont.getHumeGeometricSans3Light(this));

        File f = new File(C.App.Files.PROFILE_PIC);

        if (f != null && f.exists() && f.length() > 0) {

            try {

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));

                mProfilePic.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {

                Log.e(C.App.LOG_TAG, "Erro durante conversao de bitmap: " + f, e);

            }

        } else {

            mProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_contact_picture_holo_light));

        }

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
    public void openEvents() {
        Intent itt = new Intent(this, AgendaActivity.class);
        startActivity(itt);
    }

    @OnClick(R.id.main_produtos)
    public void openProducts() {
        Intent itt = new Intent(this, ProdutoActivity.class);
        startActivity(itt);
    }

    @Override
    public void onBackPressed() {

        HstgUtil.logout(this);

    }
}