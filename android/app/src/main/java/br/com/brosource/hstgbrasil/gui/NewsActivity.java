package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.gui.adapter.NewsAdapter;
import br.com.brosource.hstgbrasil.model.Noticia;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.NewsListHandler;
import br.com.brosource.hstgbrasil.util.CustomFont;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ButterKnife.inject(this);

        labelNews.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        btnTopo.setTypeface(CustomFont.getHumeGeometricSans3Light(this));

        HstgRestClient.getNewsList(new NewsListHandler() {
            @Override
            public void onSuccess(ArrayList<Noticia> list) {

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
