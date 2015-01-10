package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.gui.adapter.NewsAdapter;
import br.com.brosource.hstgbrasil.model.Noticia;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.NewsListHandler;
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

    @Override
    public void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isClosed()) {
            // TODO retorna pra tela de login do aplicativo
            HstgUtil.logout(this);
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

//        final AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
//        builder.setTitle(getResources().getText(R.string.news_tit));
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("AppCi");
//        View v = getLayoutInflater().inflate(R.layout.layout_dialog_news, null);
//
//        ImageView imageView = (ImageView) v.findViewById(R.id.img_new);
//        TextView txtTitulo = (TextView) v.findViewById(R.id.tit_new);
//        TextView txtNoticia = (TextView) v.findViewById(R.id.text_new);
//        TextView txtData = (TextView) v.findViewById(R.id.data_new);
//
//        txtTitulo.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
//        txtNoticia.setTypeface(CustomFont.getHumeGeometricSans3Light(this));
//        txtData.setTypeface(CustomFont.getHumeGeometricSans3Thin(this));
//
//        ImageLoader imageLoader = ImageLoader.getInstance();
//
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_news_default)
//                .build();
//
//        imageLoader.displayImage(listNoticia.get(position).getImagem(), imageView, options);
//        txtTitulo.setText(listNoticia.get(position).getTitulo());
//        txtNoticia.setText(listNoticia.get(position).getTexto());
//        txtData.setText(listNoticia.get(position).getData());
//
//        builder.setView(v);
//        builder.setPositiveButton(android.R.string.ok, null);
//
//        final AlertDialog dialog = builder.create();
//
//        dialog.show();

        SweetAlertDialog d = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);

        d.setTitleText(listNoticia.get(position).getTitulo());
        d.setContentText(listNoticia.get(position).getTexto());
        d.setCustomImage(newsAdapter.getImage(position));


        d.show();
    }
}
