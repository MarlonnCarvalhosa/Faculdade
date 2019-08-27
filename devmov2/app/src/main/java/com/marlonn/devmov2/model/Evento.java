package com.marlonn.devmov2.model;

import java.io.Serializable;

public class Evento implements Serializable {

    private String id;
    private String idEvento;
    private String nomeDoEvento;
    private String inicioDoEvento;
    private String fimDoEvento;
    private String descricaoDoEvento;
    private String latitude;
    private String longitude;
    private Boolean eventoOn;

    public Evento(String id, String idEvento, String nomeDoEvento, String inicioDoEvento, String fimDoEvento, String descricaoDoEvento, boolean eventoOn, String latitude, String longitude) {

        this.id = id;
        this.idEvento = idEvento;
        this.nomeDoEvento = nomeDoEvento;
        this.inicioDoEvento = inicioDoEvento;
        this.fimDoEvento = fimDoEvento;
        this.descricaoDoEvento = descricaoDoEvento;
        this.eventoOn = eventoOn;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Evento () {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
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

    public void setEventoOn(Boolean eventoOn) {
        this.eventoOn = eventoOn;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}