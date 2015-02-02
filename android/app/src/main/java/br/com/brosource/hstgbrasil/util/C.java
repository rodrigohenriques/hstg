package br.com.brosource.hstgbrasil.util;

import android.os.Environment;

/**
 * Created by rodrigohenriques on 11/9/14.
 */
public class C {
    public static class App {
        public static final String LOG_TAG = "HSTG-APP";
        public static final String HASHTAG = "hstgbrazil";
        public static final String NAME = "Hashtag Brazil";

        public static class Path {
            public static final String MAIN = Environment.getExternalStorageDirectory() + "/hstg";
            public static final String USER = MAIN + "/user";
        }

        public static class Files {
            public static final String PROFILE_PIC = Path.USER + "/profile_pic.png";
        }
    }

    public class Params {
        public static final String PRODUTO = "produto";
    }
}
