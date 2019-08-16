package com.example.marlonncarvalhosa.academiapersonal.model;

import java.io.Serializable;

public class Dias implements Serializable {

    private String id;
    private String dia;

    public Dias(String id, String dia) {

        this.id = id;
        this.dia = dia;

    }

    public Dias() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return dia;
    }

    public void setNome(String nome) {
        this.dia = nome;
    }
}