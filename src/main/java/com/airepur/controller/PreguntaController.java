package com.airepur.controller;

import com.airepur.domain.PreguntaDTO;
import com.airepur.service.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/preguntes")
public class PreguntaController {

    @Autowired
    PreguntaService preguntaService;

    @GetMapping
    public ArrayList<PreguntaDTO> getPreguntes(String idioma) throws SQLException {
        return preguntaService.getPreguntes(idioma);
    }

    @GetMapping(path = "/{nump}")
    public PreguntaDTO getPregunta(@PathVariable("nump") Integer nump) throws SQLException {
        return preguntaService.getPregunta(nump);
    }

    @GetMapping(path = "/{nump}/{opcio}")
    public boolean esPreguntaCorrecta(@PathVariable("nump") Integer nump, @PathVariable("opcio") Integer opcio) throws SQLException {
        return preguntaService.esPreguntaCorrecta(nump, opcio);
    }

    @GetMapping(path = "/random/{idioma}")
    public ArrayList<PreguntaDTO> getPreguntesRandoms(@PathVariable("idioma") String idioma) throws SQLException {
        return preguntaService.getPreguntesRandoms(idioma);
    }
}
