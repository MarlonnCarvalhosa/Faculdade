package com.marlonncarvalhosa.mamaeeuquero.model;

public class Mensagem {
    private String nome;
    private String texto;
    private String id;
    private Usuario usuario;


    public Mensagem() {
    }

    public Mensagem(String nome, String texto, String id, Usuario usuario) {

        this.nome = nome;
        this.texto = texto;
        this.id = id;
        this.usuario = usuario;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

