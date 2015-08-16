package br.com.brosource.hstgbrasil.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.model.Gallery;
import br.com.brosource.hstgbrasil.util.CustomFont;

/**
 * Created by haroldoolivieri on 11/20/14.
 */
public class GalleriesAdapter extends BaseAdapter {

    private List<Gallery> objects;
    private Context ctx;

    public GalleriesAdapter(Context context, List<Gallery> objects) {
        this.ctx = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Gallery getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getObjectId().hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_main_list, parent, false);
            holder = new ViewHolder();

            holder.content = convertView.findViewById(R.id.list_item_content);
            holder.txtMain = (TextView) convertView.findViewById(R.id.list_item_hashtag);

            holder.txtMain.setTypeface(CustomFont.getHumeGeometricSans3Bold(ctx));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Gallery gallery = getItem(position);

        holder.txtMain.setText(gallery.getHashtagFormatted());
        holder.content.setBackgroundColor(gallery.getIntColor());

        return convertView;

    }

    private class ViewHolder {
        TextView txtMain;
        View content;
    }
}
