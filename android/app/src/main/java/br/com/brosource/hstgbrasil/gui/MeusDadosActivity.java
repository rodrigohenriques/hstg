package br.com.brosource.hstgbrasil.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.server.GraphClient;
import br.com.brosource.hstgbrasil.util.C;
import br.com.brosource.hstgbrasil.util.CustomFont;
import br.com.brosource.hstgbrasil.util.Prefs;
import br.com.brosource.hstgbrasil.widgets.ImageViewCircle;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by rodrigohenriques on 1/31/15.
 */
public class MeusDadosActivity extends HstgActivity {

    @InjectView(R.id.dados_profile_pic)
    ImageViewCircle mProfilePic;

    @InjectView(R.id.dados_text_saudacao)
    TextView mTxtSaudacao;

    @InjectView(R.id.txt_meus_dados)
    TextView mTxtMeusDados;

    @InjectView(R.id.form)
    View form;

    @InjectView(R.id.nome)
    View nome;

    LoginButton authButton;

    Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados);

        ButterKnife.inject(this);

        authButton = (LoginButton) findViewById(R.id.authButton);

        authButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));

        prefs = new Prefs(this);

        mTxtMeusDados.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
    }

    @OnClick(R.id.btn_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.enviar_dados)
    public void enviarDados() {

    }

    @Override
    public void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            // Carrega imagem e saudacao
            if (session != null && session.isOpened()) {
                // Get the user's data.
                makeMeRequest(session);
            }

            form.setVisibility(View.GONE);
            nome.setVisibility(View.VISIBLE);
        } else {
            form.setVisibility(View.VISIBLE);
            nome.setVisibility(View.GONE);
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
                                mTxtSaudacao.setText("Olá, " + user.getName());

                                prefs.put(Prefs.Keys.USERNAME, user.getName());
                                prefs.put(Prefs.Keys.BIRTHDAY, user.getBirthday());
                                prefs.put(Prefs.Keys.EMAIL, user.asMap().get("email").toString());


                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
    }

    private void checkProfilePicture() {
        File f = new File(C.App.Files.PROFILE_PIC);

        if (f != null && f.exists() && f.length() > 0) {

            try {

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
                mProfilePic.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {

                Log.e(C.App.LOG_TAG, "Erro durante conversao de bitmap: " + f, e);
                mProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_contact_picture_holo_light));

            }

        } else {

            mProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_contact_picture_holo_light));

        }
    }
}