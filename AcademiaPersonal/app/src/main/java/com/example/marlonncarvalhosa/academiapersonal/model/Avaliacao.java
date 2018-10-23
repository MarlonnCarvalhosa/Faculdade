package com.example.marlonncarvalhosa.academiapersonal.model;

import java.io.Serializable;

public class Avaliacao implements Serializable {

    private String id;
    private String idAluno;
    private String idPersonal;
    private String bracoDireito;
    private String bracoEsquerdo;
    private String anteBracoDireito;
    private String anteBracoEsquerdo;
    private String torax;
    private String abdomen;
    private String cintura;
    private String quadril;
    private String coxaDireita;
    private String coxaEsquerda;
    private String panturrilhaDireita;
    private String panturrilhaEsquerda;
    private String peso;
    private String altura;
    private String imc;

    public Avaliacao(String id, String idAluno, String  idPersonal, String bracoDireito, String bracoEsquerdo, String anteBracoDireito, String anteBracoEsquerdo, String torax, String abdomen, String cintura, String quadril,
                     String coxaDireita, String coxaEsquerda, String panturrilhaDireita, String panturrilhaEsquerda, String peso, String altura, String imc) {

        this.id = id;
        this.idAluno = idAluno;
        this.idPersonal = idPersonal;
        this.bracoDireito = bracoDireito;
        this.bracoEsquerdo = bracoEsquerdo;
        this.anteBracoDireito = anteBracoDireito;
        this.anteBracoEsquerdo = anteBracoEsquerdo;
        this.torax = torax;
        this.abdomen = abdomen;
        this.cintura = cintura;
        this.quadril = quadril;
        this.coxaDireita = coxaDireita;
        this.coxaEsquerda = coxaEsquerda;
        this.panturrilhaDireita = panturrilhaDireita;
        this.panturrilhaEsquerda = panturrilhaEsquerda;
        this.peso= peso;
        this.altura = altura;
        this.imc = imc;

    }

    public Avaliacao() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(String idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getBracoDireito() {
        return bracoDireito;
    }

    public void setBracoDireito(String bracoDireito) {
        this.bracoDireito = bracoDireito;
    }

    public String getBracoEsquerdo() {
        return bracoEsquerdo;
    }

    public void setBracoEsquerdo(String bracoEsquerdo) {
        this.bracoEsquerdo = bracoEsquerdo;
    }

    public String getAnteBracoDireito() {
        return anteBracoDireito;
    }

    public void setAnteBracoDireito(String anteBracoDireito) {
        this.anteBracoDireito = anteBracoDireito;
    }

    public String getAnteBracoEsquerdo() {
        return anteBracoEsquerdo;
    }

    public void setAnteBracoEsquerdo(String anteBracoEsquerdo) {
        this.anteBracoEsquerdo = anteBracoEsquerdo;
    }

    public String getTorax() {
        return torax;
    }

    public void setTorax(String torax) {
        this.torax = torax;
    }

    public String getAbdomen() {
        return abdomen;
    }

    public void setAbdomen(String abdomen) {
        this.abdomen = abdomen;
    }

    public String getCintura() {
        return cintura;
    }

    public void setCintura(String cintura) {
        this.cintura = cintura;
    }

    public String getQuadril() {
        return quadril;
    }

    public void setQuadril(String quadril) {
        this.quadril = quadril;
    }

    public String getCoxaDireita() {
        return coxaDireita;
    }

    public void setCoxaDireita(String coxaDireita) {
        this.coxaDireita = coxaDireita;
    }

    public String getCoxaEsquerda() {
        return coxaEsquerda;
    }

    public void setCoxaEsquerda(String coxaEsquerda) {
        this.coxaEsquerda = coxaEsquerda;
    }

    public String getPanturrilhaDireita() {
        return panturrilhaDireita;
    }

    public void setPanturrilhaDireita(String panturrilhaDireita) {
        this.panturrilhaDireita = panturrilhaDireita;
    }

    public String getPanturrilhaEsquerda() {
        return panturrilhaEsquerda;
    }

    public void setPanturrilhaEsquerda(String panturrilhaEsquerda) {
        this.panturrilhaEsquerda = panturrilhaEsquerda;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

}
