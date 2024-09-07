package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuggestedPlaceDTO {
    private String municipi;
    private int ICA;
    private Float distancia;

    public SuggestedPlaceDTO(String municipi, Integer ICA, Float distancia) {
        this.municipi = municipi;
        this.ICA = ICA;
        this.distancia = distancia;
    }

    public String getMunicipi() {
        return municipi;
    }

    public void setMunicipi(String municipi) {
        this.municipi = municipi;
    }

    public int getICA() {
        return ICA;
    }

    public void setICA(int ICA) {
        this.ICA = ICA;
    }

    public Float getDistancia() {
        return distancia;
    }

    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }
}