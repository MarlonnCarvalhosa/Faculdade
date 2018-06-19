package com.marlonncarvalhosa.mamaeeuquero.model;

import java.io.Serializable;

public class Imagem implements Serializable{
    private String url;
    private String path;

    public Imagem(String url, String path) {
        this.url = url;
        this.path = path;
    }

    public Imagem() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
