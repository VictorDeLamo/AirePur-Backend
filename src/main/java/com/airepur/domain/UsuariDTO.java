package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuariDTO {

    private String username;
    private String nom;
    private Integer edat;
    private Integer tlf;
    private String email;
    private String pwd;
    private String idioma;
    private String fotoPerfil;
    private Boolean administrador;
    private Boolean isBlocked;
    private String localitzacio;

    public UsuariDTO(String username, String nom, Integer edat, Integer tlf, String email, String pwd, String idioma, String fotoPerfil, Boolean administrador, String lDTO, Boolean isBlocked) {
        this.username = username;
        this.nom = nom;
        this.edat = edat;
        this.tlf = tlf;
        this.email = email;
        this.pwd = pwd;
        this.idioma = idioma;
        this.fotoPerfil = fotoPerfil;
        this.administrador = administrador;
        this.isBlocked = isBlocked;
        this.localitzacio = lDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getEdat() {
        return edat;
    }

    public void setEdat(Integer edat) {
        this.edat = edat;
    }

    public Integer getTlf() {
        return tlf;
    }

    public void setTlf(Integer tlf) {
        this.tlf = tlf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public String getLocalitzacio() {
        return localitzacio;
    }

    public void setLocalitzacio(String localitzacio) {
        this.localitzacio = localitzacio;
    }
}
