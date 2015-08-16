package br.com.brosource.hstgbrasil.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.gui.adapter.GalleriesAdapter;
import br.com.brosource.hstgbrasil.model.Gallery;
import br.com.brosource.hstgbrasil.util.Constants;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class GalleriesActivity extends HstgActivity {

    @InjectView(R.id.listview)
    ListView mListView;

    GalleriesAdapter mAdapter;

    @Override
    public void onSessionStateChange(Session session, SessionState state, Exception exception) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleries);

        ButterKnife.inject(this);

        loadData();
    }

    @OnItemClick(R.id.listview)
    public void onItemClick(int position) {
        Gallery object = mAdapter.getItem(position);

        Intent intent = new Intent(this, GalleryActivity.class);

        intent.putExtra(Constants.Extras.HASHTAG, object.getHashtag());
        intent.putExtra(Constants.Extras.HEX_COLOR, object.getIntColor());

        startActivity(intent);
    }

    private void loadData() {

        startProgress();

        ParseQuery<Gallery> query = Gallery.query();
        query.findInBackground(new FindCallback<Gallery>() {

            @Override
            public void done(List<Gallery> list, ParseException e) {

                if (e != null) {
                    Log.e(Constants.App.LOG_TAG, e.getMessage(), e);

                    communicateFailure();
                } else {
                    mAdapter = new GalleriesAdapter(GalleriesActivity.this, list);
                    mListView.setAdapter(mAdapter);
                }

                finishProgress();
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.button_top)
    public void toTop() {
        mListView.setSelection(0);
    }
}
