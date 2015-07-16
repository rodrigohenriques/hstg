package br.com.brosource.hstgbrasil.gui.adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.model.InstagramPicture;

/**
 * Created by haroldoolivieri on 11/13/14.
 */
public class InstagramPictureAdapter extends ArrayAdapter<InstagramPicture> {

    List<InstagramPicture> instagramPictures;
    Activity context;

    public InstagramPictureAdapter(Activity context, List<InstagramPicture> instagramPictures) {
        super(context, 0, instagramPictures);

        this.instagramPictures = instagramPictures;
        this.context = context;
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

        String imagem = instagramPictures.get(position).getThumbnail().getUrl();

        if (imagem != null && imagem.length() > 0) {
            Picasso.with(context)
                    .load(imagem)
                    .placeholder(new ColorDrawable(context.getResources().getColor(R.color.rosa)))
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

        return convertView;

    }
}
