package com.airepur.controller;

import com.airepur.domain.DadesEstacioDTO;
import com.airepur.domain.SuggestedPlaceDTO;
import com.airepur.service.DadesEstacioService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/dadesestacio")
public class DadesEstacioController {
    @Autowired
    private DadesEstacioService dadesEstacioService = new DadesEstacioService();

    @GetMapping
    public ArrayList<DadesEstacioDTO> getDadesEstacions() throws SQLException {
        return dadesEstacioService.getDadesEstacions();
    }

    @GetMapping(path = "/{codiestacio}/{fecha}/{contaminant}")
    public DadesEstacioDTO getDadesEstacioPK(@PathVariable("codiestacio") String codiEstacio, @PathVariable("fecha") String fecha, @PathVariable("contaminant")  String contaminant) {
        return dadesEstacioService.getDadesEstacioPK(codiEstacio, fecha, contaminant);
    }

    @GetMapping(path = "/{codiestacio}")
    public ArrayList<DadesEstacioDTO> getDadesEstacioLocalitzacioAvui(@PathVariable("codiestacio") String codiEstacio) throws SQLException {
        return dadesEstacioService.getDadesEstacioLocalitzacioAvui(codiEstacio);
    }

    @GetMapping(path = "/desde/{codiestacio}/{fecha}/{contaminant}")
    public ArrayList<DadesEstacioDTO> getDadesEstacioLocalitzacioDesdeData(@PathVariable("codiestacio") String codiEstacio, @PathVariable("fecha") String fecha, @PathVariable("contaminant")  String contaminant ) throws SQLException {
        return dadesEstacioService.getDadesEstacioLocalitzacioDesdeData(codiEstacio, fecha, contaminant);
    }

    @GetMapping(path = "/ICA/{codiestacio}")
    public Integer getICAactual(@PathVariable("codiestacio") String codiEstacio) throws SQLException {
        return dadesEstacioService.getICAactual(codiEstacio);
    }

    @GetMapping(path = "/suggestedplaces/{longitud}/{latitud}")
    public ArrayList<SuggestedPlaceDTO> getSuggestedPlaces(@PathVariable("longitud") float longitud, @PathVariable("latitud") float latitud) throws SQLException, JSONException {
        return dadesEstacioService.getSuggestedPlaces(longitud, latitud);
    }

    @PostMapping(path = "/postdadesestacio")
    public DadesEstacioDTO saveDadesEstacio(@RequestBody DadesEstacioDTO dadesEstacio) {
        return dadesEstacioService.saveDadesEstacio(dadesEstacio);
    }
}
