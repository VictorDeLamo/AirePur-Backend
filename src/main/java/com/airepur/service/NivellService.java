package com.airepur.service;

import com.airepur.domain.NivellDTO;
import com.airepur.repository.NivellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NivellService {

    @Autowired
    public NivellRepository nivellRepository;

    // Método setter para inyectar el mock del repositorio en los tests
    public void setNivellRepository(NivellRepository nivellRepository) {
        this.nivellRepository = nivellRepository;
    }

    public ArrayList<NivellDTO> getNivells() {
        return nivellRepository.getNivells();
    }

    public NivellDTO getNivell(Integer nivell) {
        return nivellRepository.getNivell(nivell);
    }
}
