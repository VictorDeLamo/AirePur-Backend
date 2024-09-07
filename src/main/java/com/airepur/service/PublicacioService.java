package com.airepur.service;

import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.PublicacioDTO;
import com.airepur.repository.PreguntaRepository;
import com.airepur.repository.PublicacioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class PublicacioService {

    @Autowired
    PublicacioRepository publicacioRepository = new PublicacioRepository();

    public ArrayList<PublicacioDTO> getPublicacions() throws SQLException {
        return  publicacioRepository.getPublicacions();
    }

    public PublicacioDTO getPublicacio(String id) throws SQLException {
        return publicacioRepository.getPublicacio(id);
    }

    public void savePublicacio(String username, String contingut, String imatge, String fecha, String hora, String idPublicacio, String idanswered, String urlAvatar) {
        publicacioRepository.savePublicacio(username, contingut, imatge, fecha, hora, idPublicacio, idanswered, urlAvatar);
    }

    public void deletePublicacio(String id) throws SQLException {
        publicacioRepository.deletePublicacio(id);
    }
}