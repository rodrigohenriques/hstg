package br.com.brosource.hstgbrasil.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.control.HstgActivity;
import br.com.brosource.hstgbrasil.gui.adapter.ShapeAdapter;
import br.com.brosource.hstgbrasil.model.Gallery;
import br.com.brosource.hstgbrasil.model.Shape;
import br.com.brosource.hstgbrasil.util.Constants;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class HashtagPartyOverlayActivity extends HstgActivity {

    @InjectView(R.id.textview_title)
    TextView mTextViewTitle;

    @InjectView(R.id.imageview_photo)
    ImageView mImageViewPhoto;

    @InjectView(R.id.gridview_shapes)
    GridView mGridViewShapes;

    @InjectView(R.id.button_post)
    View mButtonPost;

    Gallery mGallery;

    ShapeAdapter mAdapter;

    Uri mPhotoUri;

    @Override
    public void onSessionStateChange(Session session, SessionState state, Exception exception) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag_party_orverlay);

        ButterKnife.inject(this);

        mPhotoUri = getIntent().getParcelableExtra(Constants.Extras.PHOTO_URI);

        mGallery = getPinnedGallery();

        if (mGallery != null) {
            mTextViewTitle.setText(mGallery.getHashtagFormatted());
            loadShapes();
        } else {
            communicateFailure();
            finish();
        }

        mImageViewPhoto.setImageURI(mPhotoUri);
    }

    @OnItemClick(R.id.gridview_shapes)
    public void onShapeItemClick(int position) {
        Shape shape = mAdapter.getItem(position);

        startProgress();

        shape.loadFile(new Shape.OnFileLoadCallback() {
            @Override
            public void onLoaded(Bitmap bitmap) {
                Drawable[] layers = new Drawable[2];
                layers[0] = new BitmapDrawable(getResources(), mPhotoUri.getPath());
                layers[1] = new BitmapDrawable(getResources(), bitmap);
                LayerDrawable layerDrawable = new LayerDrawable(layers);

                mImageViewPhoto.setImageDrawable(layerDrawable);

                finishProgress();
            }

            @Override
            public void onFailure(ParseException e) {
                communicateFailure();
                Log.e(Constants.App.LOG_TAG, e.getMessage(), e);

                finishProgress();
            }
        });
    }

    private Gallery getPinnedGallery() {
        String objectId = getIntent().getStringExtra(Constants.Extras.GALLERY_ID);

        ParseQuery<Gallery> query = Gallery.query();
        query.fromLocalDatastore();

        try {
            return query.get(objectId);
        } catch (ParseException e) {
            Log.e(Constants.App.LOG_TAG, e.getMessage(), e);
            return null;
        }
    }

    private void loadShapes() {
        startProgress();

        ParseQuery<Shape> query = Shape.query();
        query.whereEqualTo("galeria", mGallery);
        query.findInBackground(new FindCallback<Shape>() {
            @Override
            public void done(List<Shape> list, ParseException e) {

                if (e != null) {
                    Log.e(Constants.App.LOG_TAG, e.getMessage(), e);
                    communicateFailure();
                    finish();
                } else {
                    mAdapter = new ShapeAdapter(HashtagPartyOverlayActivity.this, list);
                    mGridViewShapes.setAdapter(mAdapter);


                    onShapeItemClick(0);
                }

                finishProgress();
            }
        });
    }

    @OnClick(R.id.button_post)
    public void post() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                startProgress();
                mButtonPost.setEnabled(false);
            }

            @Override
            protected Void doInBackground(Void[] params) {
                saveOverlayImage();
                createInstagramIntent(mPhotoUri);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                finishProgress();
                mButtonPost.setEnabled(true);
            }
        }.execute();
    }

    private void saveOverlayImage() {
        Drawable drawable = mImageViewPhoto.getDrawable();
        FileOutputStream out = null;

        try {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            out = new FileOutputStream(mPhotoUri.getPath());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            Log.e(Constants.App.LOG_TAG, e.getMessage(), e);
            Toast.makeText(this, "Não foi possível postar sua foto no momento.", Toast.LENGTH_LONG).show();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e(Constants.App.LOG_TAG, e.getMessage(), e);
            }
        }
    }

    private void createInstagramIntent(Uri mediaPath){
        // Create the new Intent using the 'Send' action.
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_STREAM, mediaPath);
        shareIntent.setPackage("com.instagram.android");

        // Broadcast the Intent.
        startActivity(shareIntent);
    }
}
