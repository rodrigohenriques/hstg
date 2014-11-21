package br.com.brosource.hstgbrasil.gui.adapter;

import android.content.Context;
import android.graphics.Color;
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
import br.com.brosource.hstgbrasil.util.CustomFont;

/**
 * Created by haroldoolivieri on 11/20/14.
 */
public class MainAdapter extends ArrayAdapter<String> {

    private String[] objects;
    Context ctx;

    public MainAdapter(Context context, String[] objects) {
        super(context, 0, objects);
        this.objects = objects;
        ctx = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_main_list, parent, false);
            holder = new ViewHolder();

            holder.txtMain = (TextView) convertView.findViewById(R.id.item_main_txt);
            holder.content = (View) convertView.findViewById(R.id.item_main_content);

            holder.txtMain.setTypeface(CustomFont.getHumeGeometricSans3Bold(ctx));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtMain.setText(objects[position]);
        holder.content.setBackgroundColor(getColor(position));

        return convertView;

    }

    public int getColor(int position){

        int color = 0;

        switch (position){
            case 0:
                color = ctx.getResources().getColor(R.color.amarelo);
                break;
            case 1:
                color = ctx.getResources().getColor(R.color.azul);
                break;
            case 2:
                color = ctx.getResources().getColor(R.color.rosa);
                break;
            case 3:
                color = ctx.getResources().getColor(R.color.verde);
                break;

        }

        return color;
    }

    private class ViewHolder {
        TextView txtMain;
        View content;
    }
}
