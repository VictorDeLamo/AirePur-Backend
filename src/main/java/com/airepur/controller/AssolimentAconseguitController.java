package com.airepur.controller;

import com.airepur.domain.AssolimentAconseguitDTO;
import com.airepur.domain.AssolimentDTO;
import com.airepur.service.AssolimentAconseguitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/assolimentsaconseguits")
public class AssolimentAconseguitController {

    @Autowired
    private static AssolimentAconseguitService assolimentaconseguitService = new AssolimentAconseguitService(); // borrar el new -> haurem de crear al main sempre la mateixa instancia

    @GetMapping
    public static ArrayList<AssolimentAconseguitDTO> getAssolimentAconseguit() {
        return assolimentaconseguitService.getAssoliments();
    }

    @GetMapping(path = "/{username}/{noma}")
    public static AssolimentAconseguitDTO getAssoliment(@PathVariable("username")  String username, @PathVariable("noma")  String noma){
        return assolimentaconseguitService.getAssoliment(username, noma);
    }

    @GetMapping(path = "/{username}")
    public static ArrayList<AssolimentAconseguitDTO> getAssolimentsUsuari(@PathVariable("username")  String username) {
        return assolimentaconseguitService.getAssolimentsUsuari(username);
    }

    @GetMapping(path = "/assolimentslist/{username}")
    public static int[] getAssolimentsListUsuari(@PathVariable("username")  String username) {
        return assolimentaconseguitService.getAssolimentsListUsuari(username);
    }

    @PostMapping(path = "/saveAssolimentAcoseguit")
    public static void saveAssolimentAconseguit(@RequestBody Map<String, String> assrequest) {
        String username = assrequest.get("username");
        String noma = assrequest.get("contingut");
        assolimentaconseguitService.saveAssolimentAconseguit(username, noma);
    }
}
