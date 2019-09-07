package com.marlonn.devmov2.model;

import java.io.Serializable;

public class Evento implements Serializable {

    private String idEvento;
    private String idUsuario;
    private String nomeDoEvento;
    private String inicioDoEvento;
    private String fimDoEvento;
    private String descricaoDoEvento;
    private String horaDeInicio;
    private String horaDeTerminar;
    private double latitude;
    private double longitude;
    private Boolean eventoOn;

    public Evento(String idEvento, String idUsuario, String nomeDoEvento, String inicioDoEvento, String fimDoEvento, String descricaoDoEvento, String horaDeInicio, String horaDeTerminar, boolean eventoOn, double latitude, double longitude) {

        this.idEvento = idEvento;
        this.idUsuario = idUsuario;
        this.nomeDoEvento = nomeDoEvento;
        this.inicioDoEvento = inicioDoEvento;
        this.fimDoEvento = fimDoEvento;
        this.descricaoDoEvento = descricaoDoEvento;
        this.horaDeInicio = horaDeInicio;
        this.horaDeTerminar = horaDeTerminar;
        this.eventoOn = eventoOn;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Evento () {}

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
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

    public Boolean getEventoOn(boolean b) {
        return eventoOn;
    }

    public String getHoraDeInicio() {
        return horaDeInicio;
    }

    public void setHoraDeInicio(String horaDeInicio) {
        this.horaDeInicio = horaDeInicio;
    }

    public String getHoraDeTerminar() {
        return horaDeTerminar;
    }

    public void setHoraDeTerminar(String horaDeTerminar) {
        this.horaDeTerminar = horaDeTerminar;
    }

    public Boolean getEventoOn() {
        return eventoOn;
    }

    public void setEventoOn(Boolean eventoOn) {
        this.eventoOn = eventoOn;
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
}