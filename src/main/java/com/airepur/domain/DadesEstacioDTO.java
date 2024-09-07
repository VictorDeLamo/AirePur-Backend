package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class DadesEstacioDTO {
    private LocalitzacioEstacioDTO localitzacioEstacioDTO;
    private String fecha;
    private float h01, h02, h03, h04, h05, h06, h07, h08, h09, h10, h11, h12, h13, h14, h15, h16, h17, h18, h19, h20, h21, h22, h23, h24;
    private String contaminant;
    private float quantitat;
    private String unitat;

    public DadesEstacioDTO(LocalitzacioEstacioDTO localitzacioEstacioDTO, String fecha, String contaminant, String unitat) {
        this.localitzacioEstacioDTO = localitzacioEstacioDTO;
        this.fecha = fecha;
        this.contaminant = contaminant;
        this.unitat = unitat;
    }

    public LocalitzacioEstacioDTO getLocalitzacioEstacioDTO() {
        return localitzacioEstacioDTO;
    }

    public void setLocalitzacioEstacioDTO(LocalitzacioEstacioDTO localitzacioEstacioDTO) {
        this.localitzacioEstacioDTO = localitzacioEstacioDTO;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getH01() {
        return h01;
    }

    public void setH01(float h01) {
        this.h01 = h01;
    }

    public float getH02() {
        return h02;
    }

    public void setH02(float h02) {
        this.h02 = h02;
    }

    public float getH03() {
        return h03;
    }

    public void setH03(float h03) {
        this.h03 = h03;
    }

    public float getH04() {
        return h04;
    }

    public void setH04(float h04) {
        this.h04 = h04;
    }

    public float getH05() {
        return h05;
    }

    public void setH05(float h05) {
        this.h05 = h05;
    }

    public float getH06() {
        return h06;
    }

    public void setH06(float h06) {
        this.h06 = h06;
    }

    public float getH07() {
        return h07;
    }

    public void setH07(float h07) {
        this.h07 = h07;
    }

    public float getH08() {
        return h08;
    }

    public void setH08(float h08) {
        this.h08 = h08;
    }

    public float getH09() {
        return h09;
    }

    public void setH09(float h09) {
        this.h09 = h09;
    }

    public float getH10() {
        return h10;
    }

    public void setH10(float h10) {
        this.h10 = h10;
    }

    public float getH11() {
        return h11;
    }

    public void setH11(float h11) {
        this.h11 = h11;
    }

    public float getH12() {
        return h12;
    }

    public void setH12(float h12) {
        this.h12 = h12;
    }

    public float getH13() {
        return h13;
    }

    public void setH13(float h13) {
        this.h13 = h13;
    }

    public float getH14() {
        return h14;
    }

    public void setH14(float h14) {
        this.h14 = h14;
    }

    public float getH15() {
        return h15;
    }

    public void setH15(float h15) {
        this.h15 = h15;
    }

    public float getH16() {
        return h16;
    }

    public void setH16(float h16) {
        this.h16 = h16;
    }

    public float getH17() {
        return h17;
    }

    public void setH17(float h17) {
        this.h17 = h17;
    }

    public float getH18() {
        return h18;
    }

    public void setH18(float h18) {
        this.h18 = h18;
    }

    public float getH19() {
        return h19;
    }

    public void setH19(float h19) {
        this.h19 = h19;
    }

    public float getH20() {
        return h20;
    }

    public void setH20(float h20) {
        this.h20 = h20;
    }

    public float getH21() {
        return h21;
    }

    public void setH21(float h21) {
        this.h21 = h21;
    }

    public float getH22() {
        return h22;
    }

    public void setH22(float h22) {
        this.h22 = h22;
    }

    public float getH23() {
        return h23;
    }

    public void setH23(float h23) {
        this.h23 = h23;
    }

    public float getH24() {
        return h24;
    }

    public void setH24(float h24) {
        this.h24 = h24;
    }

    public String getContaminant() {
        return contaminant;
    }

    public void setContaminant(String contaminant) {
        this.contaminant = contaminant;
    }

    public float getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(float quantitat) {
        this.quantitat = quantitat;
    }

    public String getUnitat() {
        return unitat;
    }

    public void setUnitat(String unitat) {
        this.unitat = unitat;
    }
}

