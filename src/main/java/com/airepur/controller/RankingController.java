package com.airepur.controller;

import com.airepur.domain.InfoComunitatDTO;
import com.airepur.domain.RankingComunitatDTO;
import com.airepur.domain.RankingUsuariDTO;
import com.airepur.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private RankingService rankingService;


    @PostMapping(path = "/usuaris")
    public ArrayList<RankingUsuariDTO> rankingUsers(@RequestBody Map<String, String> userRequest) {
        String username = userRequest.get("username");
        ArrayList<RankingUsuariDTO> list = rankingService.rankingUsers();
        list.add(rankingService.infoUser(username));
        return list;
    }
    @GetMapping(path = "/comunitats")
    public ArrayList<RankingComunitatDTO> rankingComunitats() {
        return rankingService.rankingComunitats();
    }

    @GetMapping(path = "/infoComunitat")
    public ArrayList<InfoComunitatDTO> infoComunitat() {
        return rankingService.infoComunitat();
    }
}
