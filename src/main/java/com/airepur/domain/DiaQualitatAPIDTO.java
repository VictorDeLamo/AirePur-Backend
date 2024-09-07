package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DiaQualitatAPIDTO {

    private int ICA;
    private Float longitud;
    private Float latitud;

    public DiaQualitatAPIDTO(int ICA, Float longitud, Float latitud) {
        this.ICA = ICA;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public int getICA() {
        return ICA;
    }

    public void setICA(int ICA) {
        this.ICA = ICA;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }
}
