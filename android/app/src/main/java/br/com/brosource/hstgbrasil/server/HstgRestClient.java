package br.com.brosource.hstgbrasil.server;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;

import br.com.brosource.hstgbrasil.server.handler.AgendaListHandler;
import br.com.brosource.hstgbrasil.server.handler.NewsListHandler;
import br.com.brosource.hstgbrasil.server.handler.ProdutoListHandler;
import br.com.brosource.hstgbrasil.util.Constants;

/**
 * Created by rodrigohenriques on 11/1/14.
 */
public class HstgRestClient {
    private static final String BASE_URL = "http://usehstg.com.br/api";
    private static final String NEWS_LIST = "/news/list";
    private static final String AGENDA_LIST = "/agenda/list";
    private static final String PRODUTO_LIST = "/produto/list";

    private static final String TOKEN = "?params=eyAgInRva2VuIiA6ICJkM2I0MWQ5MTllODUxMjI0YzI5ZGU5ZjUzZGM5MTk2OSIgIH0%3D";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getNewsList(NewsListHandler responseHandler) {
        client.get(getAbsoluteUrl(NEWS_LIST + TOKEN), null, responseHandler);
    }

    public static void getAgendaList(AgendaListHandler responseHandler) {
        client.get(getAbsoluteUrl(AGENDA_LIST + TOKEN), null, responseHandler);
    }

    public static void getProdutoList(ProdutoListHandler responseHandler) {
        client.get(getAbsoluteUrl(PRODUTO_LIST + TOKEN), null, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        String url = BASE_URL + relativeUrl;

        Log.i(Constants.App.LOG_TAG, url);

        return url;
    }
}
