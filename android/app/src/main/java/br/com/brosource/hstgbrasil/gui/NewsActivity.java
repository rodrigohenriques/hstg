package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.adapter.NewsAdapter;
import br.com.brosource.hstgbrasil.model.Noticia;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.NewsListHandler;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsActivity extends Activity {

    NewsAdapter newsAdapter;
    @InjectView(R.id.list_news)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news);

        ButterKnife.inject(this);

        HstgRestClient.getNewsList(new NewsListHandler() {
            @Override
            public void onSuccess(ArrayList<Noticia> list) {

                newsAdapter = new NewsAdapter(getApplicationContext(), list);
                listView.setAdapter(newsAdapter);

            }
        });
    }
}
