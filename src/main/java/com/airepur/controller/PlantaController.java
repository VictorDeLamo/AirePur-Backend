package com.airepur.controller;

import com.airepur.domain.PlantaDTO;
import com.airepur.service.PlantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/plantes")
public class PlantaController {

    @Autowired
    private PlantaService plantaService;

    @GetMapping
    public ArrayList<PlantaDTO> getPlantes() {
        return plantaService.getPlantes();
    }

    @PostMapping(path = "/toplant")
    public String toPlant(@RequestBody Map<String, String> toPlantRequest) {
        String username = toPlantRequest.get("username");
        return plantaService.toPlant(username);
    }

    @GetMapping(path = "/{username}")
    public PlantaDTO getPlantaUsuari(@PathVariable("username") String username) {
        return plantaService.getPlantaUsuari(username);
    }

    @PatchMapping(path = "/update/{username}")
    public String updateDataPlanta(@PathVariable("username") String username, @RequestBody Map<String, String> plantaRequest) {
        Integer puntuacio = Integer.valueOf(plantaRequest.get("puntuacio"));
        return plantaService.updateDataPlanta(username, puntuacio);
    }
}
