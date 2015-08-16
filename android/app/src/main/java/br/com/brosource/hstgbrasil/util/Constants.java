package br.com.brosource.hstgbrasil.util;

import android.os.Environment;

public class Constants {
    public static class App {
        public static final String LOG_TAG = "HSTG-APP";
        public static final String HASHTAG = "hstgbrazil";
        public static final String NAME = "Hashtag Brazil";
        public static final String PAGE_LINK = "https://www.facebook.com/hashtagpartyofficial";

        public static class Path {
            public static final String MAIN = Environment.getExternalStorageDirectory() + "/hstg";
            public static final String USER = MAIN + "/user";
        }

        public static class Files {
            public static final String PROFILE_PIC = Path.USER + "/profile_pic.png";
        }
    }

    public class Extras {
        public static final String PRODUTO = "produto";
        public static final String HASHTAG = "hashtag";
        public static final String HEX_COLOR = "hexColor";
        public static final String GALLERY_ID = "gallery_id";
        public static final String PHOTO_URI = "photo_uri";
    }
}
