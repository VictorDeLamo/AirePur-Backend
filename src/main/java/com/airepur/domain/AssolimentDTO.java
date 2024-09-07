package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssolimentDTO {

    private String nomA;
    private String descripcio;

    public AssolimentDTO(String nom, String descripcio) {
        this.nomA = nom;
        this.descripcio = descripcio;
    }

    public String getNom() {
        return nomA;
    }

    public void setNom(String nom) {
        this.nomA = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
}
