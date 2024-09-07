package com.airepur.service;

import com.airepur.domain.AssolimentAconseguitDTO;
import com.airepur.repository.AssolimentAconseguitRepository;
import com.airepur.repository.AssolimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AssolimentAconseguitService {

    @Autowired
    AssolimentAconseguitRepository assolimentAconseguitRepository = new AssolimentAconseguitRepository();
    public ArrayList<AssolimentAconseguitDTO> getAssoliments() {
        return assolimentAconseguitRepository.getAssoliments();
    }

    public AssolimentAconseguitDTO getAssoliment(String username, String noma) {
        return assolimentAconseguitRepository.getAssoliment(username, noma);
    }

    public ArrayList<AssolimentAconseguitDTO> getAssolimentsUsuari(String username) {
        return assolimentAconseguitRepository.getAssolimentsUsuari(username);
    }

    public int[] getAssolimentsListUsuari(String username) {
        return assolimentAconseguitRepository.getAssolimentsListUsuari(username);
    }

    public void saveAssolimentAconseguit(String username, String noma) {
        assolimentAconseguitRepository.saveAssolimentAconseguit(username, noma);
    }


}
