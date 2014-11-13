package br.com.brosource.hstgbrasil.model;

import br.com.brosource.hstgbrasil.util.HstgUtil;

/**
 * Created by rodrigohenriques on 11/12/14.
 */
public class Produto {
    private int produto_id;
    private String titulo;
    private String descricao;
    private String preco;
    private String imagem;


    public int getProduto_id() {
        return produto_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPreco() {
        return preco;
    }

    public String getImagem() {
        return imagem;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "produto_id=" + produto_id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco='" + preco + '\'' +
                ", imagem='" + imagem + '\'' +
                '}';
    }

    public static Produto load(String json) {
        return HstgUtil.GSON.fromJson(json, Produto.class);
    }
}
