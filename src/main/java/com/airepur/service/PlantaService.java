package com.airepur.service;

import com.airepur.domain.PlantaDTO;
import com.airepur.repository.PlantaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlantaService {

    @Autowired
    private PlantaRepository plantaRepository;

    // Método setter para inyectar el mock del repositorio en los tests
    public void setPlantaRepository(PlantaRepository plantaRepository) {
        this.plantaRepository = plantaRepository;
    }

    public ArrayList<PlantaDTO> getPlantes() {
        return plantaRepository.getPlantes();
    }

    public String toPlant(String username) {
        return plantaRepository.toPlant(username);
    }

    public PlantaDTO getPlantaUsuari(String username) {
        return plantaRepository.getPlantaUsuari(username);
    }

    public String updateDataPlanta(String username, Integer puntuacio) {
        return plantaRepository.updateDataPlanta(username, puntuacio);
    }
}
