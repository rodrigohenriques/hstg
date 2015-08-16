package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.gui.adapter.InstagramPictureAdapter;
import br.com.brosource.hstgbrasil.model.InstagramPicture;
import br.com.brosource.hstgbrasil.server.InstagramClient;
import br.com.brosource.hstgbrasil.server.handler.InstagramPictureListHandler;
import br.com.brosource.hstgbrasil.util.Constants;
import br.com.brosource.hstgbrasil.util.CustomFont;
import br.com.brosource.hstgbrasil.util.Instagram;
import br.com.brosource.hstgbrasil.util.Prefs;
import br.com.brosource.hstgbrasil.widgets.ButteryProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GalleryActivity extends Activity {

    @InjectView(R.id.hstg_header)
    View mHeader;
    @InjectView(R.id.gridview_gallery)
    GridView mGridView;
    @InjectView(R.id.textview_no_items)
    TextView mTextViewNoItems;
    @InjectView(R.id.textview_gallery_subheader)
    TextView mTextViewSubHeader;
    @InjectView(R.id.button_top)
    TextView mTextViewTop;

    Prefs mPrefs;

    ButteryProgressBar mProgressBar;

    InstagramPictureAdapter mInstagramPictureAdapter;
    ArrayList<InstagramPicture> mInstagramPictures;

    String mHashtag;
    int mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        ButterKnife.inject(this);

        mHashtag = getIntent().getStringExtra(Constants.Extras.HASHTAG);
        mColor = getIntent().getIntExtra(Constants.Extras.HEX_COLOR, 0);

        mHeader.setBackgroundColor(mColor);
        mTextViewSubHeader.setTextColor(mColor);
        mTextViewSubHeader.setText(String.format("#%s", mHashtag));
        mGridView.setBackgroundColor(mColor);

        mTextViewSubHeader.setTypeface(CustomFont.getHumeGeometricSans3Bold(this));
        mTextViewTop.setTypeface(CustomFont.getHumeGeometricSans3Light(this));

        mProgressBar = new ButteryProgressBar(this);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 24));

        final FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        decorView.addView(mProgressBar);

        ViewTreeObserver observer = mProgressBar.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View contentView = decorView.findViewById(android.R.id.content);
                mProgressBar.setY(contentView.getY());

                ViewTreeObserver observer = mProgressBar.getViewTreeObserver();
                observer.removeGlobalOnLayoutListener(this);
            }
        });

        mPrefs = new Prefs(this);

        Uri data = getIntent().getData();

        if (data != null) {
            String[] info = data.toString().split(Instagram.PARAM_ACCESS_TOKEN + "=");

            try {
                if (info[1] != null) {

                    String token = info[1];

                    mPrefs.put(Prefs.Keys.INSTAGRAM_TOKEN, token);
                } else {
                    finish();
                }
            } catch(Exception e) {
                finish();
            }
        }

        InstagramClient.searchPostByHashtag(mPrefs.get(Prefs.Keys.INSTAGRAM_TOKEN), mHashtag, new InstagramPictureListHandler() {
            @Override
            public void onSuccess(ArrayList<InstagramPicture> list) {
                Log.e(Constants.App.LOG_TAG, list.toString());

                mInstagramPictures = list;
                mProgressBar.setVisibility(View.INVISIBLE);

                mInstagramPictureAdapter = new InstagramPictureAdapter(GalleryActivity.this, list, mColor);

                if (mInstagramPictureAdapter.getCount() > 0) {
                    mGridView.setAdapter(mInstagramPictureAdapter);
                } else {
                    mTextViewNoItems.setVisibility(View.VISIBLE);
                }
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent itt = new Intent(GalleryActivity.this, InstagramPagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list_instagram", mInstagramPictures);
                bundle.putInt("position", position);

                itt.putExtras(bundle);

                startActivity(itt);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        finish();
    }

    @OnClick(R.id.btn_back)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.button_top)
    public void toTop() {
        mGridView.setSelection(0);
    }
}
