package com.example.marlonncarvalhosa.academiapersonal.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String id;
    private String personalAluno;
    private String selectAcademia;
    private int idade;

    public Usuario (String id, String personalAluno, String selectAcademia, int idade) {

        this.id = id;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

}
