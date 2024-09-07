package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlantaDTO {

    private String lastAnswer;
    private Integer puntuacioAlNivell;
    private Integer puntuacioTotal;
    private NivellDTO nivell;
    private UsuariDTO usuari;

    private Integer consecutivedays;

    public PlantaDTO(String lastAnswer, Integer puntuacioAlNivell, Integer puntuacioTotal, NivellDTO nivell, UsuariDTO usuari, Integer consecutivedays) {
        this.lastAnswer = lastAnswer;
        this.puntuacioAlNivell = puntuacioAlNivell;
        this.puntuacioTotal = puntuacioTotal;
        this.nivell = nivell;
        this.usuari = usuari;
        this.consecutivedays = consecutivedays;
    }

    public String getLastAnswer() {
        return lastAnswer;
    }

    public void setLastAnswer(String lastAnswer) {
        this.lastAnswer = lastAnswer;
    }

    public Integer getPuntuacioAlNivell() {
        return puntuacioAlNivell;
    }

    public void setPuntuacioAlNivell(Integer puntuacioAlNivell) {
        this.puntuacioAlNivell = puntuacioAlNivell;
    }

    public Integer getPuntuacioTotal() {
        return puntuacioTotal;
    }

    public void setPuntuacioTotal(Integer puntuacioTotal) {
        this.puntuacioTotal = puntuacioTotal;
    }

    public NivellDTO getNivell() {
        return nivell;
    }

    public void setNivell(NivellDTO nivell) {
        this.nivell = nivell;
    }

    public UsuariDTO getUsuari() {
        return usuari;
    }

    public void setUsuari(UsuariDTO usuari) {
        this.usuari = usuari;
    }

    public Integer getConsecutivedays() {
        return consecutivedays;
    }

    public void setConsecutivedays(Integer consecutivedays) {
        this.consecutivedays = consecutivedays;
    }
}
