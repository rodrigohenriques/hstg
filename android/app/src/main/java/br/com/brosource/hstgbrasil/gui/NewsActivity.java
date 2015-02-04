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
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.gui.adapter.NewsAdapter;
import br.com.brosource.hstgbrasil.model.Noticia;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.NewsListHandler;
import br.com.brosource.hstgbrasil.util.C;
import br.com.brosource.hstgbrasil.util.CustomFont;
import br.com.brosource.hstgbrasil.util.HstgUtil;
import br.com.brosource.hstgbrasil.widgets.ButteryProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class NewsActivity extends HstgActivity {

    NewsAdapter newsAdapter;
    @InjectView(R.id.list_news)
    ListView listView;
    @InjectView(R.id.btn_back)
    ImageView btnBack;
    @InjectView(R.id.txt_news)
    TextView labelNews;
    @InjectView(R.id.txt_topo)
    TextView btnTopo;

    ButteryProgressBar progressBar;
    ArrayList<Noticia> listNoticia;

    Noticia noticia;

    @Override
    public void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            if (noticia != null) {
                if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
                        FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
                    // Publish the post using the Share Dialog
                    FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(NewsActivity.this)
                            .setName(C.App.NAME)
                            .setCaption(noticia.getTitulo())
                            .setDescription(noticia.getTexto())
                            .setPicture(noticia.getImagem())
                            .setLink(noticia.getLink())
                            .build();

                    uiHelper.trackPendingDialogCall(shareDialog.present());
                } else {
                    // Fallback. For example, publish the post using the Feed Dialog
                    HstgUtil.publishFeedDialog(this, noticia.getTitulo(), noticia.getTexto(), noticia.getLink(), noticia.getImagem());
                }

                noticia = null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

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


        labelNews.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        btnTopo.setTypeface(CustomFont.getHumeGeometricSans3Light(this));

        HstgRestClient.getNewsList(new NewsListHandler() {
            @Override
            public void onSuccess(ArrayList<Noticia> list) {

                listNoticia = list;
                progressBar.setVisibility(View.INVISIBLE);

                newsAdapter = new NewsAdapter(getApplicationContext(), list);
                listView.setAdapter(newsAdapter);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNewsDialog(position);
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


    private void showNewsDialog(int position) {

        noticia = listNoticia.get(position);

        SweetAlertDialog d = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);

        d.setTitleText(noticia.getTitulo());
        d.setContentText(noticia.getTexto());
        d.setCustomImage(newsAdapter.getImage(position));
        d.setCancelText("Fechar");
        d.setConfirmText("Compartilhar");
        d.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Session session = Session.getActiveSession();

                if (!session.isOpened() && !session.isClosed()) {
                    session.openForRead(new Session.OpenRequest(NewsActivity.this).setPermissions(Arrays.asList("public_profile","email")).setCallback(callback));
                } else {
                    Session.openActiveSession(NewsActivity.this, true, callback);
                }

                sweetAlertDialog.dismissWithAnimation();
            }
        });

        d.show();
    }
}
