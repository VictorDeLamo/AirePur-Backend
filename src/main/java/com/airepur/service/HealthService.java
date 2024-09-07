package com.airepur.service;

import com.airepur.repository.HealthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    @Autowired
    HealthRepository healthRepository = new HealthRepository();

    public String getHealth() {
        return healthRepository.getHealth();
    }
}
