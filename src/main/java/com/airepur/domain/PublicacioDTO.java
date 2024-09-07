package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PublicacioDTO {

    private String idPublicacio;
    private String contingut;
    private String imatge;
    private String fecha;
    private String hora;
    private UsuariDTO usuari;
    private PublicacioDTO answered;
    private String urlAvatar;

    public PublicacioDTO(String idPublicacio, String contingut, String imatge, String fecha, String hora, UsuariDTO usuari, String urlAvatar) {
        this.idPublicacio = idPublicacio;
        this.contingut = contingut;
        this.imatge = imatge;
        this.fecha = fecha;
        this.hora = hora;
        this.usuari = usuari;
        this.urlAvatar = urlAvatar;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getIdPublicacio() {
        return idPublicacio;
    }

    public void setIdPublicacio(String idPublicacio) {
        this.idPublicacio = idPublicacio;
    }

    public String getContingut() {
        return contingut;
    }

    public void setContingut(String contingut) {
        this.contingut = contingut;
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

    public UsuariDTO getUsuari() {
        return usuari;
    }

    public void setUsuari(UsuariDTO usuari) {
        this.usuari = usuari;
    }

    public PublicacioDTO getAnswered() {
        return answered;
    }

    public void setAnswered(PublicacioDTO answered) {
        this.answered = answered;
    }
}