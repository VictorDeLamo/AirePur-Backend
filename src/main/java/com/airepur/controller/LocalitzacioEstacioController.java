package com.airepur.controller;

import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.service.LocalitzacioEstacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/localitzacionsestacio")
public class LocalitzacioEstacioController {

    @Autowired
    private LocalitzacioEstacioService localitzacioEstacioService;

    @GetMapping
    public ArrayList<LocalitzacioEstacioDTO> getLocalitzacions() throws SQLException {
        return localitzacioEstacioService.getLocalitzacions();
    }

    @GetMapping(path = "/{codiestacio}")
    public LocalitzacioEstacioDTO getLocalitzacio(@PathVariable("codiestacio") String codiEstacio) throws SQLException {
        return localitzacioEstacioService.getLocalitzacio(codiEstacio);
    }

    @GetMapping(path = "/{longitud}/{latitud}")
    public LocalitzacioEstacioDTO getLocalitzacioCoordenades(@PathVariable("longitud") float longitud, @PathVariable("latitud") float latitud) throws SQLException {
        return localitzacioEstacioService.getLocalitzacioCoordenades(longitud, latitud);
    }

    @PostMapping(path = "/postlocalitzacioestacio")
    public LocalitzacioEstacioDTO saveLocalitzacioEstacio(@RequestBody LocalitzacioEstacioDTO localitzacioEstacio) throws SQLException {
        return localitzacioEstacioService.saveLocalitzacioEstacio(localitzacioEstacio);
    }

    @GetMapping(path = "/municipis")
    public Map<String, String> getLocalitzacioMunicipis() throws SQLException {
        return localitzacioEstacioService.getLocalitzacioMunicipis();
    }
}
