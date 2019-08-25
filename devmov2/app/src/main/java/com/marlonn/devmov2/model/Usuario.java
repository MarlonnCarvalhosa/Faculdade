package com.marlonn.devmov2.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String fotoPerfilGoogle;
    private String idUsuario;

    public Usuario (String id, String nome, String fotoPerfilGoogle, String idUsuario) {

        this.id = id;
        this.nome = nome;
        this.fotoPerfilGoogle = fotoPerfilGoogle;
        this.idUsuario = idUsuario;

    }

    public Usuario () {
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

    public String getFotoPerfilGoogle() {
        return fotoPerfilGoogle;
    }

    public void setFotoPerfilGoogle(String fotoPerfilGoogle) {
        this.fotoPerfilGoogle = fotoPerfilGoogle;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

}
