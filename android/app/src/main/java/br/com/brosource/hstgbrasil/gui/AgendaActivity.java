package br.com.brosource.hstgbrasil.gui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.FacebookDialog;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.gui.adapter.AgendaAdapter;
import br.com.brosource.hstgbrasil.model.Evento;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.AgendaListHandler;
import br.com.brosource.hstgbrasil.util.C;
import br.com.brosource.hstgbrasil.util.CustomFont;
import br.com.brosource.hstgbrasil.util.HstgUtil;
import br.com.brosource.hstgbrasil.widgets.ButteryProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AgendaActivity extends HstgActivity {

    AgendaAdapter agendaAdapter;

    @InjectView(R.id.list_agenda)
    ListView listView;
    @InjectView(R.id.btn_back)
    ImageView btnBack;
    @InjectView(R.id.txt_agenda)
    TextView labelAgenda;
    @InjectView(R.id.txt_topo)
    TextView btnTopo;

    ButteryProgressBar progressBar;

    Evento evento;

    @Override
    public void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            if (evento != null) {
                if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
                        FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
                    // Publish the post using the Share Dialog
                    FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(AgendaActivity.this)
                            .setName(C.App.NAME)
                            .setLink(C.App.PAGE_LINK)
                            .setCaption(evento.getTitulo())
                            .setDescription(evento.getTexto())
                            .setPicture(evento.getImagem())
                            .build();

                    uiHelper.trackPendingDialogCall(shareDialog.present());
                } else {
                    // Fallback. For example, publish the post using the Feed Dialog
                    HstgUtil.publishFeedDialog(this, evento.getTitulo(), evento.getTexto(), C.App.PAGE_LINK, evento.getImagem());
                }

                evento = null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

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

        labelAgenda.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        btnTopo.setTypeface(CustomFont.getHumeGeometricSans3Light(this));

        HstgRestClient.getAgendaList(new AgendaListHandler() {
            @Override
            public void onSuccess(ArrayList<Evento> list) {

                progressBar.setVisibility(View.INVISIBLE);
                agendaAdapter = new AgendaAdapter(getApplicationContext(), list);
                listView.setAdapter(agendaAdapter);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEventDialog(position);
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.txt_topo)
    public void toTop() {
        listView.setSelection(0);
    }

    private void showEventDialog(int position) {

        evento = agendaAdapter.getItem(position);

        SweetAlertDialog d = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);

        d.setTitleText(evento.getTitulo());
        d.setContentText(evento.getTexto());
        d.setCustomImage(agendaAdapter.getImage(position));
        d.setCancelText("Fechar");
        d.setConfirmText("Compartilhar");
        d.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Session session = Session.getActiveSession();

                if (!session.isOpened() && !session.isClosed()) {
                    session.openForRead(new Session.OpenRequest(AgendaActivity.this).setPermissions(Arrays.asList("public_profile", "email")).setCallback(callback));
                } else {
                    Session.openActiveSession(AgendaActivity.this, true, callback);
                }

                sweetAlertDialog.dismissWithAnimation();
            }
        });

        d.show();
    }

}
