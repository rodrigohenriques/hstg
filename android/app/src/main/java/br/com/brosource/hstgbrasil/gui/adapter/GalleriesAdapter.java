package br.com.brosource.hstgbrasil.gui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.util.CustomFont;

/**
 * Created by haroldoolivieri on 11/20/14.
 */
public class GalleriesAdapter extends ArrayAdapter<String> {

    private String[] objects;
    private String[] hexColors;
    Context ctx;

    public GalleriesAdapter(Context context, String[] objects, String[] hexColors) {
        super(context, 0, objects);
        this.objects = objects;
        this.hexColors = hexColors;
        this.ctx = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_main_list, parent, false);
            holder = new ViewHolder();

            holder.content = convertView.findViewById(R.id.list_item_content);
            holder.txtMain = (TextView) convertView.findViewById(R.id.list_item_hashtag);

            holder.txtMain.setTypeface(CustomFont.getHumeGeometricSans3Bold(ctx));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtMain.setText(objects[position]);
        holder.content.setBackgroundColor(Color.parseColor(hexColors[position]));

        return convertView;

    }

    private class ViewHolder {
        TextView txtMain;
        View content;
    }
}
