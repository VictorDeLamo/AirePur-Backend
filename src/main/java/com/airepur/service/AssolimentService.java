package com.airepur.service;

import com.airepur.domain.AssolimentDTO;
import com.airepur.repository.AssolimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AssolimentService {

    @Autowired
    AssolimentRepository assolimentRepository = new AssolimentRepository(); // borrar el new -> haurem de crear al main sempre la mateixa instancia

    public ArrayList<AssolimentDTO> getAssoliments() {
        return (ArrayList<AssolimentDTO>) assolimentRepository.getAssoliments();
    }

    public AssolimentDTO getAssoliment(String noma){
        return assolimentRepository.getAssoliment(noma);
    }
}
