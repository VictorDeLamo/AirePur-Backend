package com.airepur.controller;

import com.airepur.domain.DiaQualitatAPIDTO;
import com.airepur.service.DiaQualitatAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/airepurapi")
public class DiaQualitatAPIController {

    @Autowired
    private static DiaQualitatAPIService diaQualitatAPIService = new DiaQualitatAPIService();

    @GetMapping
    public ArrayList<DiaQualitatAPIDTO> getInfoAPI() {
        return diaQualitatAPIService.getInfoAPI();
    }
}
