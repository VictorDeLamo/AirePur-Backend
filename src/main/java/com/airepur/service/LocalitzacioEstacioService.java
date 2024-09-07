package com.airepur.service;

import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.repository.LocalitzacioEstacioRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class LocalitzacioEstacioService {

    @Autowired
    private LocalitzacioEstacioRepository localitzacioEstacioRepository;

    public void setLocalitzacioEstacioRepository(LocalitzacioEstacioRepository localitzacioEstacioRepository) {
        this.localitzacioEstacioRepository = localitzacioEstacioRepository;
    }

    public ArrayList<LocalitzacioEstacioDTO> getLocalitzacions() throws SQLException {
        return localitzacioEstacioRepository.getLocalitzacions();
    }

    public LocalitzacioEstacioDTO getLocalitzacio(String codiEstacio) throws SQLException {
        return localitzacioEstacioRepository.getLocalitzacio(codiEstacio);
    }

    public LocalitzacioEstacioDTO getLocalitzacioCoordenades(float longitud, float latitud) throws SQLException {
        return localitzacioEstacioRepository.getLocalitzacioCoordenades(longitud, latitud);
    }

    public LocalitzacioEstacioDTO saveLocalitzacioEstacio(LocalitzacioEstacioDTO localitzacioEstacio) throws SQLException {
        return localitzacioEstacioRepository.saveLocalitzacioEstacio(localitzacioEstacio);
    }

    public float Haversine(float longitud1, float latitud1, float longitud2, float latitud2) {
        final float R = 6371.0f;
        float latDistance = (float) Math.toRadians(latitud2 - latitud1);
        float lonDistance = (float) Math.toRadians(longitud2 - longitud1);
        float a = (float) (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitud1)) * Math.cos(Math.toRadians(latitud2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));
        float c = 2 * (float) Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public Map<String, String> getLocalitzacioMunicipis() throws SQLException {
        return localitzacioEstacioRepository.getLocalitzacioMunicipis();
    }
}
