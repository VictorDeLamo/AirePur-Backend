package com.airepur.controller;

import com.airepur.domain.NivellDTO;
import com.airepur.service.NivellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/nivells")
public class NivellController {

    @Autowired
    private NivellService nivellService;

    @GetMapping
    public ArrayList<NivellDTO> getNivells() {
        return nivellService.getNivells();
    }

    @GetMapping(path = "/{nivell}")
    public NivellDTO getNivell(@PathVariable("nivell") Integer nivell) {
        return nivellService.getNivell(nivell);
    }
}
