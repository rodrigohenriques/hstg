package br.com.brosource.hstgbrasil.gui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

    Produto produto;

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

        produto = getArguments() != null ? (Produto) getArguments().getSerializable(C.Params.PRODUTO) : null;

        Picasso.with(getActivity())
                .load(produto.getImagem())
                .placeholder(new ColorDrawable(getActivity().getResources().getColor(R.color.verde)))
                .into(imgProduto);

        txtNomeProduto.setTypeface(CustomFont.getHumeGeometricSans3Bold(getActivity()));
        txtDescProduto.setTypeface(CustomFont.getHumeGeometricSans3Light(getActivity()));
        txtPreco.setTypeface(CustomFont.getHumeGeometricSans3Bold(getActivity()));

        txtNomeProduto.setText(produto.getTitulo());
        txtDescProduto.setText(produto.getDescricao());
        txtPreco.setText("R$" + produto.getPreco());

        imgProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(produto.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return v;
    }

}