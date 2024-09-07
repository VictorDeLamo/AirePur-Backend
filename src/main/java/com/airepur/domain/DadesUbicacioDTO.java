package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DadesUbicacioDTO {

    private UsuariDTO usuari;
    private Integer qualitatAire;
    private LocalitzacioEstacioDTO localitzacio;
    private String fecha;
    private String hora;
    private String municipi;

    public DadesUbicacioDTO(UsuariDTO usuari, LocalitzacioEstacioDTO localitzacio, String fecha, String hora) {
        this.usuari = usuari;
        this.localitzacio = localitzacio;
        this.fecha = fecha;
        this.hora = hora;
    }

    public UsuariDTO getUsuari() {
        return usuari;
    }

    public void setUsuari(UsuariDTO usuari) {
        this.usuari = usuari;
    }

    public Integer getQualitatAire() {
        return qualitatAire;
    }

    public void setQualitatAire(Integer qualitatAire) {
        this.qualitatAire = qualitatAire;
    }

    public LocalitzacioEstacioDTO getLocalitzacio() {
        return localitzacio;
    }

    public void setLocalitzacio(LocalitzacioEstacioDTO localitzacio) {
        this.localitzacio = localitzacio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMunicipi() {
        return municipi;
    }

    public void setMunicipi(String municipi) {
        this.municipi = municipi;
    }
}
