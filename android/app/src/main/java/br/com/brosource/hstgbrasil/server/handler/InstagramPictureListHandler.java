package br.com.brosource.hstgbrasil.server.handler;

import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.brosource.hstgbrasil.model.InstagramPicture;
import br.com.brosource.hstgbrasil.util.Constants;

/**
 * Created by rodrigohenriques on 11/9/14.
 */
public abstract class InstagramPictureListHandler extends TextHttpResponseHandler {

    final String DATA = "data";
    final String LINK = "link";
    final String IMAGES = "images";
    final String LOW_RESOLUTION = "low_resolution";
    final String THUMBNAIL = "thumbnail";
    final String STANDARD_RESOLUTION = "standard_resolution";
    final String URL = "url";
    final String WIDTH = "width";
    final String HEIGHT = "height";

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.e(Constants.App.LOG_TAG, "Erro de conversao de String para JsonArray", throwable);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.i(Constants.App.LOG_TAG, responseString);

        try {
            ArrayList<InstagramPicture> list = new ArrayList<InstagramPicture>();

            JSONObject object = new JSONObject(responseString);

            JSONArray array = object.getJSONArray(DATA);

            for (int i = 0; i < array.length() && i < 20; i++) {

                JSONObject pictureObject = array.getJSONObject(i);

                String url = pictureObject.getString(LINK);

                InstagramPicture picture = new InstagramPicture(url);

                JSONObject images = pictureObject.getJSONObject(IMAGES);

                JSONObject lowResolution = images.getJSONObject(LOW_RESOLUTION);
                JSONObject thumbnail = images.getJSONObject(THUMBNAIL);
                JSONObject standardResolution = images.getJSONObject(STANDARD_RESOLUTION);

                picture.setLowResolution(lowResolution.getString(URL), lowResolution.getInt(WIDTH), lowResolution.getInt(HEIGHT));
                picture.setThumbnail(thumbnail.getString(URL), thumbnail.getInt(WIDTH), thumbnail.getInt(HEIGHT));
                picture.setStandardResolution(standardResolution.getString(URL), standardResolution.getInt(WIDTH), standardResolution.getInt(HEIGHT));

                list.add(picture);

                Log.i(Constants.App.LOG_TAG, picture.toString());
            }

            onSuccess(list);
        } catch (JSONException e) {
            Log.e(Constants.App.LOG_TAG, "Erro de conversao de String para JsonArray", e);
        }
    }

    public abstract void onSuccess(ArrayList<InstagramPicture> list);
}
