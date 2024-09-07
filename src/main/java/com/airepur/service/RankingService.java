package com.airepur.service;

import com.airepur.domain.InfoComunitatDTO;
import com.airepur.domain.RankingComunitatDTO;
import com.airepur.domain.RankingUsuariDTO;
import com.airepur.repository.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankingRepository;

    public RankingUsuariDTO infoUser(String username){
        return rankingRepository.infoUser(username);
    }
    public ArrayList<RankingUsuariDTO> rankingUsers() {
        return rankingRepository.rankingUsers();
    }

    public ArrayList<RankingComunitatDTO> rankingComunitats() {
        return rankingRepository.rankingComunitats();
    }

    public ArrayList<InfoComunitatDTO> infoComunitat() {
        return rankingRepository.infoComunitat();
    }
}