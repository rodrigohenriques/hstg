package br.com.brosource.hstgbrasil.gui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.util.CustomFont;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends HstgActivity {
    @InjectView(R.id.main_profile_pic)
    ProfilePictureView mProfilePic;
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


                                // Set the id for the ProfilePictureView
                                // view that in turn displays the profile picture.
                                mProfilePic.setProfileId(user.getId());
                                // Set the Textview's text to the user's name.
                                mTxtSaudacao.setText("Ola, " + user.getName());
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

        mNews.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mAgenda.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mGaleria.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mProdutos.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mDados.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mWiFi.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (Session.getActiveSession().isOpened()) {
            Session.getActiveSession().close();
        }
    }
}
