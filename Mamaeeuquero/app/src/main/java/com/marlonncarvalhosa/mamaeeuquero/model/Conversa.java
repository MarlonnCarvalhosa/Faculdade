package com.marlonncarvalhosa.mamaeeuquero.model;

import java.io.Serializable;

public class Conversa implements Serializable {
    private String id;
    private String idVendedor;
    private String idComprador;
    private Produto produto;


    public Conversa(String id, String idVendedor, String idComprador, Produto produto) {
        this.id = id;
        this.idVendedor = idVendedor;
        this.idComprador = idComprador;
        this.produto = produto;
    }

    public Conversa() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(String idComprador) {
        this.idComprador = idComprador;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
