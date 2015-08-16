package br.com.brosource.hstgbrasil.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;

import java.util.Arrays;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.util.Constants;
import br.com.brosource.hstgbrasil.util.CustomFont;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends HstgActivity {
    @InjectView(R.id.inicial_textview_entrar)
    TextView mTxtEntrar;

    @Override
    public void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(Constants.App.LOG_TAG, "Aplicacao ja logada, prosseguir pra proxima tela");

            startActivity(new Intent(this, MainActivity.class));

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);
        mTxtEntrar.setTypeface(CustomFont.getHumeGeometricSans3Thin(this));
    }

    @OnClick(R.id.inicial_button_login_facebook)
    public void loginFacebook() {
        Session session = Session.getActiveSession();

        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setPermissions(Arrays.asList("public_profile")).setCallback(callback));
        } else {
            Session.openActiveSession(this, true, callback);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}