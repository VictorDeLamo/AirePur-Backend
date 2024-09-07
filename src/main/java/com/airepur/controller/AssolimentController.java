package com.airepur.controller;

import com.airepur.domain.AssolimentDTO;
import com.airepur.service.AssolimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/assoliments")
public class AssolimentController {

    @Autowired
    private static AssolimentService assolimentService = new AssolimentService(); // borrar el new -> haurem de crear al main sempre la mateixa instancia

    @GetMapping
    public static ArrayList<AssolimentDTO> getAssoliments() {
        return assolimentService.getAssoliments();
    }

    @GetMapping(path = "/{noma}")
    public static AssolimentDTO getAssoliment(@PathVariable("noma")  String noma){
        return assolimentService.getAssoliment(noma);
    }
}
