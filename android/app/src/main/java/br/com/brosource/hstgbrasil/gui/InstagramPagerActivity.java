package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.model.InstagramPicture;

/**
 * Created by haroldoolivieri on 5/29/14.
 */
public class InstagramPagerActivity extends Activity {

    private ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    int position;
    List<InstagramPicture> instagramPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_galeria_gallery);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        Bundle bundle = getIntent().getExtras();

        position = bundle.getInt("position");
        instagramPictures = (List<InstagramPicture>) bundle.getSerializable("list_instagram");

        GalleryAdapter pagerAdapter = new GalleryAdapter(this, instagramPictures);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);
        circlePageIndicator.setViewPager(viewPager);
    }

    public class GalleryAdapter extends PagerAdapter {

        List<InstagramPicture> instagramPictures;
        ImageLoader imageLoader;
        Context context;

        public GalleryAdapter(Context context, List<InstagramPicture> instagramPictures) {
            super();
            this.context = context;
            this.instagramPictures = instagramPictures;
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getCount() {
            if (instagramPictures == null) {
                return 0;
            }
            return instagramPictures.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(context);

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_galeria_default)
                    .build();

            String imagem = instagramPictures.get(position).getStandardResolution().getUrl();

            if (imagem != null && imagem.length() > 0) {
                imageLoader.displayImage(imagem, imageView, options);
            } else {
                imageView.setVisibility(View.GONE);
            }

            ((ViewPager) container).addView(imageView);

            return imageView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView imageView = (ImageView) object;
            ((ViewPager) container).removeView(imageView);
        }
    }
}
