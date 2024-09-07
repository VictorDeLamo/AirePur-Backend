package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreguntaDTO {

    private Integer numP;
    private String contingut;
    private String correcta;
    private String opcioA;
    private String opcioB;
    private String opcioC;
    private String opcioD;
    private String idioma;

    public PreguntaDTO(Integer numP, String contingut, String correcta, String opcioA, String opcioB, String opcioC, String opcioD) {
        this.numP = numP;
        this.contingut = contingut;
        this.correcta = correcta;
        this.opcioA = opcioA;
        this.opcioB = opcioB;
        this.opcioC = opcioC;
        this.opcioD = opcioD;
    }

    public Integer getNumP() {
        return numP;
    }

    public void setNumP(Integer numP) {
        this.numP = numP;
    }

    public String getContingut() {
        return contingut;
    }

    public void setContingut(String contingut) {
        this.contingut = contingut;
    }

    public String getCorrecta() {
        return correcta;
    }

    public void setCorrecta(String correcta) {
        this.correcta = correcta;
    }

    public String getOpcioA() {
        return opcioA;
    }

    public void setOpcioA(String opcioA) {
        this.opcioA = opcioA;
    }

    public String getOpcioB() {
        return opcioB;
    }

    public void setOpcioB(String opcioB) {
        this.opcioB = opcioB;
    }

    public String getOpcioC() {
        return opcioC;
    }

    public void setOpcioC(String opcioC) {
        this.opcioC = opcioC;
    }

    public String getOpcioD() {
        return opcioD;
    }

    public void setOpcioD(String opcioD) {
        this.opcioD = opcioD;
    }

    public String getIdioma() { return idioma; }

    public void setIdioma(String idioma) { this.idioma = idioma; }
}
