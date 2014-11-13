package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.gui.adapter.NewsAdapter;
import br.com.brosource.hstgbrasil.model.Noticia;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.NewsListHandler;
import br.com.brosource.hstgbrasil.util.CustomFont;
import br.com.brosource.hstgbrasil.widgets.ButteryProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewsActivity extends Activity {

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

                progressBar.setVisibility(View.INVISIBLE);

                newsAdapter = new NewsAdapter(getApplicationContext(), list);
                listView.setAdapter(newsAdapter);

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

}
