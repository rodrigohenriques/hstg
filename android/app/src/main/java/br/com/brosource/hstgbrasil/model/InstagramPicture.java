package br.com.brosource.hstgbrasil.model;

import java.io.Serializable;

/**
 * Created by rodrigohenriques on 12/27/14.
 */
public class InstagramPicture implements Serializable{

    private String url;
    private InstagramImage thumbnail, lowResolution, standardResolution;

    public class InstagramImage implements Serializable{
        private String url;
        private int width, heigth;

        InstagramImage(String url, int width, int heigth) {
            this.url = url;
            this.width = width;
            this.heigth = heigth;
        }

        public String getUrl() {
            return url;
        }

        public int getWidth() {
            return width;
        }

        public int getHeigth() {
            return heigth;
        }

        @Override
        public String toString() {
            return "InstagramImage{" +
                    "url='" + url + '\'' +
                    ", width=" + width +
                    ", heigth=" + heigth +
                    '}';
        }
    }

    public InstagramPicture(String url) {
        this.url = url;
    }

    public void setThumbnail(String url, int width, int heigth) {
        this.thumbnail = new InstagramImage(url, width, heigth);
    }

    public void setLowResolution(String url, int width, int heigth) {
        this.lowResolution = new InstagramImage(url, width, heigth);
    }

    public void setStandardResolution(String url, int width, int heigth) {
        this.standardResolution = new InstagramImage(url, width, heigth);
    }

    public String getUrl() {
        return url;
    }

    public InstagramImage getThumbnail() {
        return thumbnail;
    }

    public InstagramImage getLowResolution() {
        return lowResolution;
    }

    public InstagramImage getStandardResolution() {
        return standardResolution;
    }

    @Override
    public String toString() {
        return "InstagramPicture{" +
                "url='" + url + '\'' +
                ", thumbnail=" + thumbnail +
                ", lowResolution=" + lowResolution +
                ", standardResolution=" + standardResolution +
                '}';
    }
}
