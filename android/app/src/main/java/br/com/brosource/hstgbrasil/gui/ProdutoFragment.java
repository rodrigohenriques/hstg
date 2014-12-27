package br.com.brosource.hstgbrasil.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import br.com.brosource.hstgbrasil.R;
import br.com.brosource.hstgbrasil.model.Produto;
import br.com.brosource.hstgbrasil.util.C;
import br.com.brosource.hstgbrasil.util.CustomFont;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class ProdutoFragment extends Fragment {

    @InjectView(R.id.img_produto)
    ImageView imgProduto;
    @InjectView(R.id.txt_nome_produto)
    TextView txtNomeProduto;
    @InjectView(R.id.txt_desc_produto)
    TextView txtDescProduto;
    @InjectView(R.id.txt_preco)
    TextView txtPreco;

    ImageLoader imageLoader;

    public static ProdutoFragment newInstance(Produto produto) {
        ProdutoFragment f = new ProdutoFragment();

        Bundle args = new Bundle();
        args.putSerializable(C.Params.PRODUTO, produto);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_carrousel_produto, container, false);
        ButterKnife.inject(this, v);

        Produto produto = getArguments() != null ? (Produto) getArguments().getSerializable(C.Params.PRODUTO) : null;

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_default_produto)
                .build();

        imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(produto.getImagem(), imgProduto, options);

        txtNomeProduto.setTypeface(CustomFont.getHumeGeometricSans3Bold(getActivity()));
        txtDescProduto.setTypeface(CustomFont.getHumeGeometricSans3Light(getActivity()));
        txtPreco.setTypeface(CustomFont.getHumeGeometricSans3Bold(getActivity()));

        txtNomeProduto.setText(produto.getTitulo());
        txtDescProduto.setText(produto.getDescricao());
        txtPreco.setText("R$" + produto.getPreco());

        return v;
    }

}