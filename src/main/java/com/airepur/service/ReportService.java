package com.airepur.service;

import com.airepur.domain.ReportDTO;
import com.airepur.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository = new ReportRepository();

    public String saveReport(String missatge, String fecha, String hora, String usuariReportador, String usuariReportat,String idpublicacio) {
        return reportRepository.saveReport(missatge, fecha, hora, usuariReportador, usuariReportat, idpublicacio);
    }

    public ArrayList<ReportDTO> getReports() {
        return reportRepository.getReports();
    }

    public boolean validToken(String token) {
        return reportRepository.validToken(token);
    }

    public String deleteReport(String idreport) {
        return reportRepository.deleteReport(idreport);
    }

    public String deleteUser(String username, String text) {
        return reportRepository.deleteUser(username, text);
    }

    public String deletePublicacio(String username, String text, String pub) {
        return reportRepository.deletePublicacio(username, text, pub);
    }
}
