package br.com.brosource.hstgbrasil.gui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.model.Evento;
import br.com.brosource.hstgbrasil.util.CustomFont;

/**
 * Created by haroldoolivieri on 11/13/14.
 */
public class AgendaAdapter extends ArrayAdapter<Evento> {

    List<Evento> eventos;
    Context context;

    ImageLoader imageLoader;
    String HOUR_PATTERN = "yyyy-MM-dd HH:mm:ss";

    List<Bitmap> images;

    public AgendaAdapter(Context context, List<Evento> eventos) {
        super(context, 0, eventos);

        this.eventos = eventos;
        this.context = context;

        imageLoader = ImageLoader.getInstance();

        images = new ArrayList<Bitmap>();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_list_agenda, parent, false);
            holder = new ViewHolder();

            holder.image = (ImageView) convertView.findViewById(R.id.item_img_evento);

            holder.txtDia = (TextView) convertView.findViewById(R.id.item_dia_evento);
            holder.txtMes = (TextView) convertView.findViewById(R.id.item_mes_evento);

            holder.txtData = (TextView) convertView.findViewById(R.id.item_txt_data_evento);
            holder.txtTitulo = (TextView) convertView.findViewById(R.id.item_txt_titulo_evento);
            holder.txtDescricao = (TextView) convertView.findViewById(R.id.item_txt_descricao_evento);

            holder.txtDia.setTypeface(CustomFont.getHumeGeometricSans3Bold(context));
            holder.txtMes.setTypeface(CustomFont.getHumeGeometricSans3Bold(context));
            holder.txtData.setTypeface(CustomFont.getHumeGeometricSans3Bold(context));
            holder.txtTitulo.setTypeface(CustomFont.getHumeGeometricSans3Bold(context));
            holder.txtDescricao.setTypeface(CustomFont.getHumeGeometricSans3Light(context));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SimpleDateFormat format = new SimpleDateFormat(HOUR_PATTERN);
        try {

            //String to date
            Date date = format.parse(eventos.get(position).getDataInicial());

            //Date to Formatted String
            SimpleDateFormat formatHora = new SimpleDateFormat("HH");
            SimpleDateFormat formatDia = new SimpleDateFormat("dd");
            SimpleDateFormat formatMes = new SimpleDateFormat("MMM");

            String horas = formatHora.format(date);
            String dia = formatDia.format(date);
            String mes = formatMes.format(date);

            holder.txtData.setText(dia + " " + mes + " - " + horas + "H");
            holder.txtDia.setText(dia);
            holder.txtMes.setText(mes);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_evento_default)
                .build();


        String imagem = eventos.get(position).getImagem();

        if (imagem != null && imagem.length() > 0) {
            imageLoader.displayImage(eventos.get(position).getImagem(), holder.image, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    images.add(position, loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
            holder.txtDia.setVisibility(View.INVISIBLE);
            holder.txtMes.setVisibility(View.INVISIBLE);
        } else {
            holder.image.setVisibility(View.GONE);
            holder.txtDia.setVisibility(View.VISIBLE);
            holder.txtMes.setVisibility(View.VISIBLE);
        }

        holder.txtTitulo.setText(eventos.get(position).getTitulo());
        holder.txtDescricao.setText(eventos.get(position).getTexto());

        return convertView;

    }

    private class ViewHolder {
        ImageView image;
        TextView txtData;
        TextView txtTitulo;
        TextView txtDescricao;

        TextView txtDia;
        TextView txtMes;
    }

    public Drawable getImage(int position) {

        Bitmap bitmap = images.get(position);
        return new BitmapDrawable(getContext().getResources(), bitmap);
    }
}
