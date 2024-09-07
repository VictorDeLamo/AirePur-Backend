package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NivellDTO {

    private Integer nivell;
    private Integer puntuacioNivell;

    public NivellDTO(Integer nivell, Integer puntuacioNivell) {
        this.nivell = nivell;
        this.puntuacioNivell = puntuacioNivell;
    }

    public Integer getNivell() {
        return nivell;
    }

    public void setNivell(Integer nivell) {
        this.nivell = nivell;
    }

    public Integer getPuntuacioNivell() {
        return puntuacioNivell;
    }

    public void setPuntuacioNivell(Integer puntuacioNivell) {
        this.puntuacioNivell = puntuacioNivell;
    }
}
