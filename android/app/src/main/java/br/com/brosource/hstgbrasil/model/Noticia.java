package br.com.brosource.hstgbrasil.model;

import br.com.brosource.hstgbrasil.util.HstgUtil;

/**
 * Created by rodrigohenriques on 11/12/14.
 */
public class Noticia {
    private int post_id;
    private String titulo;
    private String texto;
    private String data;
    private String imagem;

    public static Noticia load(String json) {
        return HstgUtil.GSON.fromJson(json, Noticia.class);
    }

    public int getPost_id() {
        return post_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public String getData() {
        return data;
    }

    public String getImagem() {
        return imagem;
    }

    @Override
    public String toString() {
        return "Noticia{" +
                "post_id=" + post_id +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", data='" + data + '\'' +
                ", imagem='" + imagem + '\'' +
                '}';
    }

    public String getLink() {
        return "http://blog.hstg.com.br/?p=" + post_id;
    }
}
