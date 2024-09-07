package com.airepur.controller;

import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.PreguntaDTO;
import com.airepur.domain.PublicacioDTO;
import com.airepur.service.PreguntaService;
import com.airepur.service.PublicacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/publicacions")
public class PublicacioController {

    @Autowired
    PublicacioService publicacioService = new PublicacioService();

    @GetMapping
    public ArrayList<PublicacioDTO> getPublicacions() throws SQLException {
        return publicacioService.getPublicacions();
    }

    @GetMapping(path = "/{id}")
    public PublicacioDTO getPublicacio(@PathVariable("id") String id) throws SQLException {
        return publicacioService.getPublicacio(id);
    }

    @PostMapping(path = "/postpublicacio")
    public void savePublicacio(@RequestBody Map<String, String> publicacioRequest) {
        String username = publicacioRequest.get("username");
        String contingut = publicacioRequest.get("contingut");
        String fecha = publicacioRequest.get("fecha");
        String hora = publicacioRequest.get("hora");
        String idPublicacio = publicacioRequest.get("idPublicacio");
        String idanswered = publicacioRequest.get("idanswered");
        String urlAvatar = publicacioRequest.get("urlAvatar");
        String imatge = publicacioRequest.get("imatge");

        publicacioService.savePublicacio(username, contingut, imatge, fecha, hora, idPublicacio, idanswered, urlAvatar);
    }
    @DeleteMapping(path = "/delete/{id}")
    public void deletePublicacio(@PathVariable("id") String id) throws SQLException {
        publicacioService.deletePublicacio(id);
    }
}