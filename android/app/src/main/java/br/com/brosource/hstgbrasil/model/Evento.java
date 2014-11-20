package br.com.brosource.hstgbrasil.model;

import br.com.brosource.hstgbrasil.util.HstgUtil;

/**
 * Created by rodrigohenriques on 11/9/14.
 */
public class Evento {
    public int event_id;
    public String data_inicial;
    public String data_final;
    public String titulo;
    public String texto;
    public String imagem;

    public static Evento load(String json) {
        return HstgUtil.GSON.fromJson(json, Evento.class);
    }

    public int getEvent_id() {
        return event_id;
    }

    public String getDataInicial() {
        return data_inicial;
    }

    public String getDataFinal() {
        return data_final;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public String getImagem() {
        return imagem;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "event_id=" + event_id +
                ", data_inicial='" + data_inicial + '\'' +
                ", data_final='" + data_final + '\'' +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", imagem='" + imagem + '\'' +
                '}';
    }
}
