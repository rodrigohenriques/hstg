package br.com.brosource.hstgbrasil.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.GetDataCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayInputStream;

@ParseClassName("Mosaicos")
public class Shape extends ParseObject {
    public static ParseQuery<Shape> query() {
        return ParseQuery.getQuery(Shape.class);
    }

    public ParseFile getThumb() {
        return getParseFile("thumb");
    }

    public ParseFile getImage() {
        return getParseFile("image");
    }

    public void loadFile(final OnFileLoadCallback callback) {
        ParseFile imageFile = getImage();
        imageFile.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    // data has the bytes for the image
                    Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(data));

                    callback.onLoaded(bitmap);
                } else {
                    // something went wrong
                    callback.onFailure(e);
                }
            }
        });
    }

    public interface OnFileLoadCallback {
        void onLoaded(Bitmap bitmap);
        void onFailure(ParseException e);
    }
}
