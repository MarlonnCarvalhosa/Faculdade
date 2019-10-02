package com.marlonn.devmov2.model;

import java.io.Serializable;

public class Evento implements Serializable {

    private String id;
    private String idUsuario;
    private String nomeDoEvento;
    private String nomeCriadorEvento;
    private String imagemEvento;
    private String inicioDoEvento;
    private String fimDoEvento;
    private String descricaoDoEvento;
    private String horaInicio;
    private String horaFim;
    private double latitude;
    private double longitude;
    private Boolean eventoOn;

    public Evento(String id, String idUsuario, String nomeDoEvento,String nomeCriadorEvento,String imagemEvento, String inicioDoEvento, String fimDoEvento, String descricaoDoEvento, String horaInicio, String horaFim, boolean eventoOn, double latitude, double longitude) {

        this.id = id;
        this.idUsuario = idUsuario;
        this.nomeDoEvento = nomeDoEvento;
        this.nomeCriadorEvento = nomeCriadorEvento;
        this.imagemEvento = imagemEvento;
        this.inicioDoEvento = inicioDoEvento;
        this.fimDoEvento = fimDoEvento;
        this.descricaoDoEvento = descricaoDoEvento;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.eventoOn = eventoOn;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Evento () {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeDoEvento() {
        return nomeDoEvento;
    }

    public void setNomeDoEvento(String nomeDoEvento) {
        this.nomeDoEvento = nomeDoEvento;
    }

    public String getNomeCriadorEvento() {
        return nomeCriadorEvento;
    }

    public void setNomeCriadorEvento(String nomeCriadorEvento) {
        this.nomeCriadorEvento = nomeCriadorEvento;
    }

    public String getImagemEvento() {
        return imagemEvento;
    }

    public void setImagemEvento(String imagemEvento) {
        this.imagemEvento = imagemEvento;
    }

    public String getInicioDoEvento() {
        return inicioDoEvento;
    }

    public void setInicioDoEvento(String inicioDoEvento) {
        this.inicioDoEvento = inicioDoEvento;
    }

    public String getFimDoEvento() {
        return fimDoEvento;
    }

    public void setFimDoEvento(String fimDoEvento) {
        this.fimDoEvento = fimDoEvento;
    }

    public String getDescricaoDoEvento() {
        return descricaoDoEvento;
    }

    public void setDescricaoDoEvento(String descricaoDoEvento) {
        this.descricaoDoEvento = descricaoDoEvento;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Boolean getEventoOn() {
        return eventoOn;
    }

    public void setEventoOn(Boolean eventoOn) {
        this.eventoOn = eventoOn;
    }
}