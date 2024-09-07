package com.airepur.service;

import com.airepur.domain.DiaQualitatAPIDTO;
import com.airepur.repository.DiaQualitatAPIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DiaQualitatAPIService {

    @Autowired
    DiaQualitatAPIRepository diaQualitatAPIRepository = new DiaQualitatAPIRepository();

    public ArrayList<DiaQualitatAPIDTO> getInfoAPI() {
        return diaQualitatAPIRepository.getInfoAPI();
    }
}
