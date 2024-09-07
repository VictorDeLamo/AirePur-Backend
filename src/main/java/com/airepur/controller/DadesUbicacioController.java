package com.airepur.controller;

import com.airepur.domain.DadesUbicacioDTO;
import com.airepur.service.DadesUbicacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/dadesubicacio")
public class DadesUbicacioController {

    @Autowired
    private DadesUbicacioService dadesUbicacioService;

    @GetMapping
    public ArrayList<DadesUbicacioDTO> getDadesUbicacions() {
        return dadesUbicacioService.getDadesUbicacions();
    }

    @GetMapping(path = "/historic/{username}")
    public ArrayList<DadesUbicacioDTO> getDadesUbicacioHistoric(@PathVariable String username) {
        return dadesUbicacioService.getDadesUbicacioHistoric(username);
    }

    @GetMapping(path = "/infodata/{username}/{fecha}")
    public DadesUbicacioDTO getInfoData(@PathVariable String username, @PathVariable String fecha) {
        return dadesUbicacioService.getInfoData(username, fecha);
    }

    @PostMapping(path = "/postdadesubicacio")
    public boolean saveDadesUbicacio(@RequestBody Map<String, String> dadesUbicacioRequest) throws SQLException {
        String longitud = dadesUbicacioRequest.get("longitud");
        String latitud = dadesUbicacioRequest.get("latitud");
        String municipi = dadesUbicacioRequest.get("municipi");
        String username = dadesUbicacioRequest.get("username");
        String fecha = dadesUbicacioRequest.get("fecha");
        String hora = dadesUbicacioRequest.get("hora");

        return dadesUbicacioService.saveDadesUbicacio(longitud, latitud, municipi, username, fecha, hora);
    }
}
