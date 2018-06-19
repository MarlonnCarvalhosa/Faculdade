package com.marlonncarvalhosa.mamaeeuquero.model;

import java.io.Serializable;
import java.util.Calendar;

public class Produto implements Serializable {

    private String id;
    private String iddovendedor;
    private String nome;
    private String cat;
    private String preco;
    private String local;
    private String descrição;
    private String dataInicial;
    private String horarioInicial;
    private String lancedocomprador;
    private String nomedocomprador;
    private int hora;
    private int minuto;
    private int segundos;
    private int dia;
    private Imagem imagem1 = new Imagem();
    private Imagem imagem2 = new Imagem();
    private Imagem imagem3 = new Imagem();

    public Produto(String id,String iddovendedor, String nome, String cat, String preco, String local, String descrição, String dataInicial, String horarioInicial, int hora, int minuto, int segundos, int dia, Imagem imagem1, Imagem imagem2, Imagem imagem3, String lancedocomprador,String nomedocomprador) {
        this.id = id;
        this.iddovendedor=iddovendedor;
        this.nome = nome;
        this.cat = cat;
        this.preco = preco;
        this.local = local;
        this.descrição = descrição;
        this.dataInicial = dataInicial;
        this.horarioInicial = horarioInicial;
        this.hora = hora;
        this.minuto = minuto;
        this.dia = dia;
        this.segundos = segundos;
        this.imagem1 = imagem1;
        this.imagem2 = imagem2;
        this.imagem3 = imagem3;
        this.lancedocomprador = lancedocomprador;
        this.nomedocomprador=nomedocomprador;
    }

    public Produto() {
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

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(String horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public Imagem getImagem1() {
        return imagem1;
    }

    public void setImagem1(Imagem imagem1) {
        this.imagem1 = imagem1;
    }

    public Imagem getImagem2() {
        return imagem2;
    }

    public void setImagem2(Imagem imagem2) {
        this.imagem2 = imagem2;
    }

    public Imagem getImagem3() {
        return imagem3;
    }

    public void setImagem3(Imagem imagem3) {
        this.imagem3 = imagem3;
    }
    public String getIddovendedor() {
        return iddovendedor;
    }

    public void setIddovendedor(String iddovendedor) {
        this.iddovendedor = iddovendedor;
    }

    public void recebeLance(String valor,String lancedocomprador, String nomedocomprador,String id ) {
        this.setPreco(valor);
        this.setLancedocomprador(id);
        this.setNomedocomprador(nomedocomprador);
        Calendar diaAtual = Calendar.getInstance();
        setMinuto(diaAtual.get(Calendar.MINUTE));
        setHora(diaAtual.get(Calendar.HOUR_OF_DAY));
        setSegundos(diaAtual.get(Calendar.SECOND));
    }

    public String getLancedocomprador() {
        return lancedocomprador;
    }

    public void setLancedocomprador(String lancedocomprador) {
        this.lancedocomprador = lancedocomprador;
    }


    public String getNomedocomprador() {
        return nomedocomprador;
    }

    public void setNomedocomprador(String nomedocomprador) {
        this.nomedocomprador = nomedocomprador;
    }
}
