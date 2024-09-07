package com.airepur.controller;

import com.airepur.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService = new HealthService();

    @GetMapping
    public String getHealth() {
        return healthService.getHealth();
    }
}
