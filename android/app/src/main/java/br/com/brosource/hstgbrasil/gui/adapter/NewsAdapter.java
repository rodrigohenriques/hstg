package br.com.brosource.hstgbrasil.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.model.Noticia;
import br.com.brosource.hstgbrasil.util.CustomFont;

/**
 * Created by haroldoolivieri on 11/12/14.
 */
public class NewsAdapter extends ArrayAdapter<Noticia> {

    Context context;
    List<Noticia> noticias;
    ImageLoader imageLoader;

    public NewsAdapter(Context context, List<Noticia> noticias) {
        super(context, 0, noticias);

        this.noticias = noticias;
        this.context = context;

        imageLoader = ImageLoader.getInstance();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_list_news, parent, false);
            holder = new ViewHolder();

            holder.image = (ImageView) convertView.findViewById(R.id.item_img_news);
            holder.txtTitulo = (TextView) convertView.findViewById(R.id.item_txt_titulo_news);
            holder.txtDescricao = (TextView) convertView.findViewById(R.id.item_txt_descricao_news);

            holder.txtTitulo.setTypeface(CustomFont.getHumeGeometricSans3Bold(context));
            holder.txtDescricao.setTypeface(CustomFont.getHumeGeometricSans3Light(context));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_news_default)
                .build();

        imageLoader.displayImage(noticias.get(position).getImagem(), holder.image, options);
        holder.txtTitulo.setText(noticias.get(position).getTitulo());
        holder.txtDescricao.setText(noticias.get(position).getTexto());

        return convertView;

    }

    private class ViewHolder {
        ImageView image;
        TextView txtTitulo;
        TextView txtDescricao;

    }
}
