package com.airepur.service;

import com.airepur.domain.DadesEstacioDTO;
import com.airepur.domain.SuggestedPlaceDTO;
import com.airepur.repository.DadesEstacioRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class DadesEstacioService {

    @Autowired
    private DadesEstacioRepository dadesEstacioRepository;

    // Método setter para inyectar el mock del repositorio en los tests
    public void setDadesEstacioRepository(DadesEstacioRepository dadesEstacioRepository) {
        this.dadesEstacioRepository = dadesEstacioRepository;
    }

    public ArrayList<DadesEstacioDTO> getDadesEstacions() throws SQLException {
        return dadesEstacioRepository.getDadesEstacions();
    }

    public DadesEstacioDTO getDadesEstacioPK(String codiEstacio, String fecha, String contaminant) {
        return dadesEstacioRepository.getDadesEstacioPK(codiEstacio, fecha, contaminant);
    }

    public ArrayList<DadesEstacioDTO> getDadesEstacioLocalitzacioAvui(String codiestacio) throws SQLException {
        return dadesEstacioRepository.getDadesEstacioLocalitzacioAvui(codiestacio);
    }

    public ArrayList<DadesEstacioDTO> getDadesEstacioLocalitzacioDesdeData(String codiEstacio, String fecha, String contaminant) {
        return dadesEstacioRepository.getDadesEstacioLocalitzacioDesdeData(codiEstacio, fecha, contaminant);
    }

    public Integer getICAactual(String codiEstacio) throws SQLException {
        return dadesEstacioRepository.getICAactual(codiEstacio);
    }

    public ArrayList<SuggestedPlaceDTO> getSuggestedPlaces(float longitud, float latitud) throws SQLException, JSONException {
        return dadesEstacioRepository.getSuggestedplaces(longitud, latitud);
    }

    public DadesEstacioDTO saveDadesEstacio(DadesEstacioDTO dadesEstacio) {
        return dadesEstacioRepository.saveDadesEstacio(dadesEstacio);
    }
}
