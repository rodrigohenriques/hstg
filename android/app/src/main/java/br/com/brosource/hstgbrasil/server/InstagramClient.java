package br.com.brosource.hstgbrasil.server;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import br.com.brosource.hstgbrasil.server.handler.InstagramPictureListHandler;
import br.com.brosource.hstgbrasil.util.Constants;
import br.com.brosource.hstgbrasil.util.Instagram;

/**
 * Created by rodrigohenriques on 12/27/14.
 */
public class InstagramClient {

    private final static String API_HOST = "https://api.instagram.com";
    private final static String SEARCH_BY_HASHTAG_RECENT = "/v1/tags/%s/media/recent";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void searchPostByHashtag(String accessToken, String hashtag, InstagramPictureListHandler handler) {
        RequestParams requestParams = new RequestParams();

        requestParams.put(Instagram.PARAM_ACCESS_TOKEN, accessToken);

        String url = getAbsoluteUrl(String.format(SEARCH_BY_HASHTAG_RECENT, hashtag));

        client.get(url, requestParams, handler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        String url = API_HOST + relativeUrl;

        Log.i(Constants.App.LOG_TAG, url);

        return url;
    }
}
