package br.com.brosource.hstgbrasil.util;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.parse.Parse;
import com.parse.ParseObject;

import br.com.brosource.hstgbrasil.model.Gallery;
import br.com.brosource.hstgbrasil.model.Shape;

public class HstgApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "zmrQgVIG05BDRA7oo9XfCXi7Te189kKPAEQJyRYv", "bKTont4rxuoRVXHwrMAt5sQsdm6KnC7NAY3itrwa");

        ParseObject.registerSubclass(Gallery.class);
        ParseObject.registerSubclass(Shape.class);
    }
}
