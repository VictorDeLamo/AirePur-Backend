package com.airepur.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReportDTO {

    private Integer idReport;
    private String missatge;
    private String fechaReport;
    private String horaReport;
    private String usuariReportat;
    private String usuariReportador;

    private String idpublicacio;

    public ReportDTO(Integer idReport, String missatge, String fechaReport, String horaReport, String usuariReportat, String usuariReportador, String idpublicacio) {
        this.idReport = idReport;
        this.missatge = missatge;
        this.fechaReport = fechaReport;
        this.horaReport = horaReport;
        this.usuariReportat = usuariReportat;
        this.usuariReportador = usuariReportador;
        this.idpublicacio = idpublicacio;
    }

    public Integer getIdReport() {
        return idReport;
    }

    public String getMissatge() {
        return missatge;
    }

    public void setMissatge(String missatge) {
        this.missatge = missatge;
    }

    public String getFechaReport() {
        return fechaReport;
    }

    public void setFechaReport(String fechaReport) {
        this.fechaReport = fechaReport;
    }

    public String getHoraReport() {
        return horaReport;
    }

    public void setHoraReport(String horaReport) {
        this.horaReport = horaReport;
    }

    public String getUsuariReportat() {
        return usuariReportat;
    }

    public void setUsuariReportat(String usuariReportat) {
        this.usuariReportat = usuariReportat;
    }

    public String getUsuariReportador() {
        return usuariReportador;
    }

    public void setUsuariReportador(String usuariReportador) {
        this.usuariReportador = usuariReportador;
    }

    public String getIdPublicacio() {
        return idpublicacio;
    }

    public void setIdReport(Integer idReport) {
        this.idReport = idReport;
    }

    public void setIdPublicacio(String idPublicacio) {
        this.idpublicacio = idPublicacio;
    }
}
