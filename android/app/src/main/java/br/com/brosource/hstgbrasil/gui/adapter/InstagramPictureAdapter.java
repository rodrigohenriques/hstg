package br.com.brosource.hstgbrasil.gui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.model.Evento;
import br.com.brosource.hstgbrasil.model.InstagramPicture;
import br.com.brosource.hstgbrasil.util.CustomFont;

/**
 * Created by haroldoolivieri on 11/13/14.
 */
public class InstagramPictureAdapter extends ArrayAdapter<InstagramPicture> {

    List<InstagramPicture> instagramPictures;
    Activity context;

    ImageLoader imageLoader;

    public InstagramPictureAdapter(Activity context, List<InstagramPicture> instagramPictures) {
        super(context, 0, instagramPictures);

        this.instagramPictures = instagramPictures;
        this.context = context;

        imageLoader = ImageLoader.getInstance();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {

            convertView = context.getLayoutInflater().inflate(R.layout.item_instagram_gallery, parent, false);

            imageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(imageView);

        } else {
            imageView = (ImageView) convertView.getTag();
        }

        int size = context.getResources().getDisplayMetrics().widthPixels / 3;

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = layoutParams.width = size;
        imageView.setLayoutParams(layoutParams);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_galeria_default)
                .build();

        String imagem = instagramPictures.get(position).getThumbnail().getUrl();

        if (imagem != null && imagem.length() > 0) {
            imageLoader.displayImage(imagem, imageView, options);
        } else {
            imageView.setVisibility(View.GONE);
        }

        return convertView;

    }
}
