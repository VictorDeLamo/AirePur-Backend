package com.airepur.service;

import com.airepur.domain.PreguntaDTO;
import com.airepur.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class PreguntaService {

    @Autowired
    PreguntaRepository preguntaRepository;

    // Método setter para inyectar el mock del repositorio en los tests
    public void setPreguntaRepository(PreguntaRepository preguntaRepository) {
        this.preguntaRepository = preguntaRepository;
    }

    public ArrayList<PreguntaDTO> getPreguntes(String idioma) throws SQLException {
        return preguntaRepository.getPreguntes(idioma);
    }

    public PreguntaDTO getPregunta(Integer nump) throws SQLException {
        return preguntaRepository.getPregunta(nump);
    }

    public boolean esPreguntaCorrecta(Integer nump, Integer opcio) throws SQLException {
        return preguntaRepository.esPreguntaCorrecta(nump, opcio);
    }

    public ArrayList<PreguntaDTO> getPreguntesRandoms(String idioma) throws SQLException {
        return preguntaRepository.getPreguntesRandoms(idioma);
    }
}
