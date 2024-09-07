package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssolimentAconseguitDTO {

    private String username;
    private String nomA;

    public AssolimentAconseguitDTO(String username, String nomA) {
        this.username = username;
        this.nomA = nomA;
    }

    public String getUsername() {
        return username;
    }

    public String getNomA() {
        return nomA;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNomA(String nomA) {
        this.nomA = nomA;
    }
}
