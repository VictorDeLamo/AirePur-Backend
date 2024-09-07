package com.airepur.controller;

import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.ReportDTO;
import com.airepur.domain.UsuariDTO;
import com.airepur.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    ReportService reportService = new ReportService();

    @GetMapping
    public ResponseEntity<ArrayList<ReportDTO>> getReports(@RequestHeader("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else if(!validToken(token)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        ArrayList<ReportDTO> reports = reportService.getReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    public boolean validToken(String token) {
        token = token.replace("Bearer ", "");
        return reportService.validToken(token);
    }

    @PostMapping(path = "/postreport")
    public String saveReport(@RequestBody Map<String, String> reportRequest) {

        String missatge = reportRequest.get("missatge");
        String fecha = reportRequest.get("fecha");
        String hora = reportRequest.get("hora");
        String usuariReportador = reportRequest.get("usernameReportador");
        String usuariReportat = reportRequest.get("usernameReportat");
        String idpublicacio = reportRequest.get("idpublicacio");
        String reports = reportService.saveReport(missatge, fecha, hora, usuariReportador, usuariReportat, idpublicacio);
        return reports;
    }

    @PostMapping(path = "/deletereport")
    public ResponseEntity<String> deleteReport(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> idreportRequest) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else if(!validToken(token)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String idreport = idreportRequest.get("id");
        String reports = reportService.deleteReport(idreport);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping(path = "/deleteuser")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> usernameRequest) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else if(!validToken(token)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String username = usernameRequest.get("username");
        String text = usernameRequest.get("text");
        String reports = reportService.deleteUser(username, text);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping(path = "/deletepublicacio")
    public ResponseEntity<String> deletePublicacio(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> usernameRequest) {

        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else if(!validToken(token)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String username = usernameRequest.get("username");
        String text = usernameRequest.get("text");
        String pub = usernameRequest.get("idpublicacio");
        String reports = reportService.deletePublicacio(username, text, pub);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

}
