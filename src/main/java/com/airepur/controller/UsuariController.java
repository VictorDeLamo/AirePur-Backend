package com.airepur.controller;

import com.airepur.domain.UsuariDTO;
import com.airepur.service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/usuaris")
public class UsuariController {

    @Autowired
    private UsuariService usuariService = new UsuariService();

    @GetMapping
    public ArrayList<UsuariDTO> getUsuaris() {
        return usuariService.getUsuaris();
    }

    @GetMapping(path = "/{username}")
    public UsuariDTO getUsuari(@PathVariable("username") String username) {
        return usuariService.getUsuari(username);
    }

    @PostMapping(path = "/login")
    public UsuariDTO login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        String email = loginRequest.get("email");
        return usuariService.usuariCorrecte(username, password, email);
    }

    @PostMapping(path = "/signup")
    public String signup(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        String email = loginRequest.get("email");
        String nom = loginRequest.get("name");
        String edat = loginRequest.get("age");
        String tlf = loginRequest.get("telefon");
        String idioma = loginRequest.get("language");
        String urlPerfil = loginRequest.get("profileImageUrl");
        boolean isBlocked = false;
        return usuariService.crearUsuari(username, password, email, nom, tlf, edat, idioma, urlPerfil, isBlocked);
    }

    @PostMapping(path = "/remove")
    public boolean remove(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        return usuariService.usuariRemove(username);
    }

    @PostMapping(path = "/toggleBlock")
    public boolean toggleBlock(@RequestBody Map<String, Object> blockRequest) {
        String username = (String) blockRequest.get("username");
        boolean isBlocked = (Boolean) blockRequest.get("isBlocked");
        return usuariService.toggleUserBlock(username, isBlocked);
    }

    @PostMapping(path = "/recover")
    public String recover(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        return usuariService.recoverPass(email);
    }

    @PostMapping(path = "/updateCS")
    public String assignarCodiStacio(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String codi = loginRequest.get("codi");
        return usuariService.assignarCodiStacio(username,codi);
    }
    @PostMapping(path = "/updateNom")
    public String newName(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String name = loginRequest.get("name");
        return usuariService.newName(username,name);
    }
    @PostMapping(path = "/updatePass")
    public String newPass(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String pass = loginRequest.get("pass");
        return usuariService.newPass(username,pass);
    }
    @PostMapping(path = "/updateEdat")
    public String newEdat(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String edat = loginRequest.get("edat");
        return usuariService.newEdat(username,edat);
    }

    @PostMapping(path = "/updateTel")
    public String newTel(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String tel = loginRequest.get("tel");
        return usuariService.newTel(username,tel);
    }
    @PostMapping(path = "/updateEmail")
    public String newEmail(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String email = loginRequest.get("email");
        return usuariService.newEmail(username,email);
    }

    @PostMapping(path = "/updateIdioma")
    public String newIdioma(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String idioma = loginRequest.get("idioma");
        return usuariService.newIdioma(username,idioma);
    }

    @PostMapping(path = "/adminToken")
    public String token(@RequestBody Map<String, String> admin) {
        String username = admin.get("username");
        String password = admin.get("password");
        return usuariService.adminToken(username, password);
    }

    @PostMapping(path = "/{username}/urlPerfil")
    public String newFotoPerfil(@PathVariable("username") String username, @RequestBody Map<String, String> urlRequest) {
        String urlPerfil = urlRequest.get("url");
        return usuariService.newFotoPerfil(username, urlPerfil);
    }

}
