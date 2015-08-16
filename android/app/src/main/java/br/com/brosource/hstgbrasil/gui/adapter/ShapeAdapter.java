package br.com.brosource.hstgbrasil.gui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.model.Shape;

public class ShapeAdapter extends BaseAdapter {

    private final List<Shape> shapes;
    private final Activity context;

    public ShapeAdapter(Activity context, List<Shape> shapes) {
        this.shapes = shapes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return shapes.size();
    }

    @Override
    public Shape getItem(int position) {
        return shapes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getObjectId().hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {

            convertView = context.getLayoutInflater().inflate(R.layout.item_gridview, parent, false);

            imageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(imageView);

        } else {
            imageView = (ImageView) convertView.getTag();
        }

        int size = context.getResources().getDisplayMetrics().widthPixels / 4;

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = layoutParams.width = size;
        imageView.setLayoutParams(layoutParams);

        String imagem = getItem(position).getThumb().getUrl();

        if (imagem != null && imagem.length() > 0) {
            Picasso.with(context)
                    .load(imagem)
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

        return convertView;

    }
}
