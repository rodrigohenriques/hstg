package br.com.brosource.hstgbrasil.gui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
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
        Context context;

        public GalleryAdapter(Context context, List<InstagramPicture> instagramPictures) {
            super();
            this.context = context;
            this.instagramPictures = instagramPictures;
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

            String imagem = instagramPictures.get(position).getStandardResolution().getUrl();

            if (imagem != null && imagem.length() > 0) {
                Picasso.with(context)
                        .load(imagem)
                        .into(imageView);
            } else {
                imageView.setVisibility(View.GONE);
            }

            container.addView(imageView);

            return imageView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView imageView = (ImageView) object;
            ((ViewPager) container).removeView(imageView);
        }
    }
}
