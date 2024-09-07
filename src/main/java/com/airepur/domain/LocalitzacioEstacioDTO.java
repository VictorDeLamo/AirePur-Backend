package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocalitzacioEstacioDTO {

    private String codiEstacio;
    private String comarca;
    private String municipi;
    private Float longitud;
    private Float latitud;

    public LocalitzacioEstacioDTO(String codiEstacio, String comarca, String municipi, Float longitud, Float latitud) {
        this.codiEstacio = codiEstacio;
        this.comarca = comarca;
        this.municipi = municipi;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public LocalitzacioEstacioDTO(String codiEstacio, String municipi) {
        this.codiEstacio = codiEstacio;
        this.municipi = municipi;
    }

    public String getCodiEstacio() {
        return codiEstacio;
    }

    public void setCodiEstacio(String codiEstacio) {
        this.codiEstacio = codiEstacio;
    }

    public String getComarca() {
        return comarca;
    }

    public void setComarca(String comarca) {
        this.comarca = comarca;
    }

    public String getMunicipi() {
        return municipi;
    }

    public void setMunicipi(String municipi) {
        this.municipi = municipi;
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
