package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InfoComunitatDTO {

    private String codi;
    private String municipi;
    private float longitud;
    private float latitud;
    private int avgNivell;


    public InfoComunitatDTO(String codi, String municipi, float longitud, float latitud, int avgNivell) {
        this.codi = codi;
        this.municipi = municipi;
        this.longitud = longitud;
        this.latitud = latitud;
        this.avgNivell = avgNivell;
    }

    public int getAvgNivell() {
        return avgNivell;
    }

    public void setAvgNivell(int avgNivell) {
        this.avgNivell = avgNivell;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getMunicipi() {
        return municipi;
    }

    public void setMunicipi(String municipi) {
        this.municipi = municipi;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }
}
