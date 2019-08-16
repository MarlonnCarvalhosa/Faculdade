package com.example.marlonncarvalhosa.academiapersonal.model;

import java.io.Serializable;

public class Exercicio implements Serializable {

    private String id;
    private String nome;

    public Exercicio (String id, String nome) {

        this.id = id;
        this.nome = nome;

    }

    public Exercicio () {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
