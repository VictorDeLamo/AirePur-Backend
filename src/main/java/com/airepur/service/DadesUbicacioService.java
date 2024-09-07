package com.airepur.service;

import com.airepur.domain.DadesUbicacioDTO;
import com.airepur.repository.DadesUbicacioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class DadesUbicacioService {

    @Autowired
    private DadesUbicacioRepository dadesUbicacioRepository;

    // Método setter para inyectar el mock del repositorio en los tests
    public void setDadesUbicacioRepository(DadesUbicacioRepository dadesUbicacioRepository) {
        this.dadesUbicacioRepository = dadesUbicacioRepository;
    }

    public ArrayList<DadesUbicacioDTO> getDadesUbicacions() {
        return dadesUbicacioRepository.getDadesUbicacions();
    }

    public DadesUbicacioDTO getInfoData(String username, String fecha) {
        return dadesUbicacioRepository.getInfoData(username, fecha);
    }

    public ArrayList<DadesUbicacioDTO> getDadesUbicacioHistoric(String username) {
        return dadesUbicacioRepository.getDadesUbicacioHistoric(username);
    }

    public boolean saveDadesUbicacio(String longitud, String latitud, String municipi, String username, String fecha, String hora) throws SQLException {
        return dadesUbicacioRepository.saveDadesUbicacio(longitud, latitud, municipi, username, fecha, hora);
    }
}
