package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.gui.ProdutoFragment;
import br.com.brosource.hstgbrasil.model.Evento;
import br.com.brosource.hstgbrasil.model.Produto;
import br.com.brosource.hstgbrasil.server.HstgRestClient;
import br.com.brosource.hstgbrasil.server.handler.AgendaListHandler;
import br.com.brosource.hstgbrasil.server.handler.ProdutoListHandler;
import br.com.brosource.hstgbrasil.util.CustomFont;
import br.com.brosource.hstgbrasil.widgets.ButteryProgressBar;
import br.com.brosource.hstgbrasil.widgets.MultiViewPager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ProdutoActivity extends FragmentActivity {

    @InjectView(R.id.txt_produto)
    TextView labelProduto;
    ButteryProgressBar progressBar;
    private MultiViewPager mPager;
    private FragmentStatePagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

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

        labelProduto.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));

        HstgRestClient.getProdutoList(new ProdutoListHandler() {
            @Override
            public void onSuccess(final ArrayList<Produto> list) {

                progressBar.setVisibility(View.INVISIBLE);

                mPager = (MultiViewPager) findViewById(R.id.produto_pager);
                mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

                    @Override
                    public int getCount() {
                        return list.size();
                    }

                    @Override
                    public Fragment getItem(int position) {
                        return new ProdutoFragment().newInstance(list.get(position));
                    }
                };
                mPager.setAdapter(mAdapter);

            }
        });
    }

    @OnClick(R.id.btn_back)
    public void back() {
        onBackPressed();
    }

}
