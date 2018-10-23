package com.example.marlonncarvalhosa.academiapersonal.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String emailGoogle;
    private String fotoPerfilGoogle;
    private String idUsuario;
    private String personalAluno;
    private String selectAcademia;
    private String idade;

    public Usuario (String id, String nome, String emailGoogle, String fotoPerfilGoogle, String idUsuario, String personalAluno, String selectAcademia, String idade) {

        this.id = id;
        this.nome = nome;
        this.emailGoogle = emailGoogle;
        this.fotoPerfilGoogle = fotoPerfilGoogle;
        this.idUsuario = idUsuario;
        this.personalAluno = personalAluno;
        this.selectAcademia = selectAcademia;
        this.idade = idade;

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

    public String getEmailGoogle() {
        return emailGoogle;
    }

    public void setEmailGoogle(String emailGoogle) {
        this.emailGoogle = emailGoogle;
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

    public String getPersonalAluno() {
        return personalAluno;
    }

    public void setPersonalAluno(String personalAluno) {
        this.personalAluno = personalAluno;
    }

    public String getSelectAcademia() {
        return selectAcademia;
    }

    public void setSelectAcademia(String selectAcademia) {
        this.selectAcademia = selectAcademia;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String  idade) {
        this.idade = idade;
    }

}
