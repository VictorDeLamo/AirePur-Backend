package com.airepur.service;

import com.airepur.domain.UsuariDTO;
import com.airepur.repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private MailService mailService;

    // Método setter para inyectar el mock del repositorio en los tests
    public void setUsuariRepository(UsuariRepository usuariRepository) {
        this.usuariRepository = usuariRepository;
    }

    public ArrayList<UsuariDTO> getUsuaris() {
        return usuariRepository.getUsuaris();
    }

    public UsuariDTO getUsuari(String username) {
        return usuariRepository.getUsuari(username);
    }

    public UsuariDTO usuariCorrecte(String username, String password, String email) {
        return usuariRepository.usuariCorrecte(username, password, email);
    }

    public String crearUsuari(String username, String password, String email, String nom, String tlf, String edat, String idioma, String urlPerfil, Boolean isBlocked) {
        return usuariRepository.crearUsuari(username, password, email, nom, tlf, edat, idioma, urlPerfil, isBlocked);
    }

    public boolean usuariRemove(String username) {
        return usuariRepository.usuariRemove(username);
    }

    public boolean toggleUserBlock(String username, boolean isBlocked) {
        return usuariRepository.toggleUserBlock(username, isBlocked);
    }

    public String recoverPass(String email) {
        String pwd = usuariRepository.updatePass(email);
        String answer = "";
        if (!pwd.equals("correo"))
            answer = mailService.recoverPass(email, pwd);
        else return "correo";
        return "ok";
    }

    public String assignarCodiStacio(String username, String codi) {
        return usuariRepository.assignarCodiStacio(username, codi);
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }


    public String newName(String username, String name) {
        return usuariRepository.newName(username, name);
    }

    public String newPass(String username, String pass) {
        return usuariRepository.newYourPass(username, pass);
    }

    public String newEdat(String username, String edat) {
        return usuariRepository.newEdat(username, edat);
    }

    public String newTel(String username, String tel) {
        return usuariRepository.newTel(username, tel);
    }

    public String newEmail(String username, String email) {
        return usuariRepository.newEmail(username, email);
    }

    public String newIdioma(String username, String idioma) {
        return usuariRepository.newIdioma(username, idioma);
    }

    public String adminToken(String username, String password) {
        return usuariRepository.adminToken(username, password);
    }

    public String newFotoPerfil(String username, String urlPerfil) {
        return usuariRepository.newFotoPerfil(username, urlPerfil);
    }
}
