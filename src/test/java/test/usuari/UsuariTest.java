package test.usuari;

import com.airepur.BackendApplication;
import com.airepur.controller.UsuariController;
import com.airepur.domain.UsuariDTO;
import com.airepur.repository.UsuariRepository;
import com.airepur.service.MailService;
import com.airepur.service.UsuariService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendApplication.class)
public class UsuariTest {

    @InjectMocks
    private UsuariController controller;

    @Spy
    private UsuariService usuariService;

    @MockBean
    private UsuariRepository usuariRepository;

    @MockBean
    private MailService mailService;  // Agregando el mock para MailService

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Asegurar que UsuariService utiliza el mock de UsuariRepository
        usuariService.setUsuariRepository(usuariRepository);
        usuariService.setMailService(mailService);  // Inyectar el mock de MailService en UsuariService

        // Configuración del mock de la conexión y el statement
        Method getConnectionMethod = UsuariRepository.class.getDeclaredMethod("getConnection");
        getConnectionMethod.setAccessible(true);
        when(getConnectionMethod.invoke(usuariRepository)).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void testGetUsuaris() throws SQLException {
        ArrayList<UsuariDTO> usuaris = new ArrayList<>();
        UsuariDTO sampleUsuari = createSampleUsuariDTO();
        usuaris.add(sampleUsuari);

        when(usuariRepository.getUsuaris()).thenReturn(usuaris);

        ArrayList<UsuariDTO> result = controller.getUsuaris();
        assertNotNull(result);
        assertEquals(usuaris.size(), result.size());

        UsuariDTO resultUsuari = result.get(0);
        assertEquals(sampleUsuari.getUsername(), resultUsuari.getUsername());
        assertEquals(sampleUsuari.getNom(), resultUsuari.getNom());
        assertEquals(sampleUsuari.getEdat(), resultUsuari.getEdat());
        assertEquals(sampleUsuari.getTlf(), resultUsuari.getTlf());
        assertEquals(sampleUsuari.getEmail(), resultUsuari.getEmail());
        assertEquals(sampleUsuari.getPwd(), resultUsuari.getPwd());
        assertEquals(sampleUsuari.getIdioma(), resultUsuari.getIdioma());
        assertEquals(sampleUsuari.getFotoPerfil(), resultUsuari.getFotoPerfil());
        assertEquals(sampleUsuari.getAdministrador(), resultUsuari.getAdministrador());
        assertEquals(sampleUsuari.getLocalitzacio(), resultUsuari.getLocalitzacio());
        assertEquals(sampleUsuari.getBlocked(), resultUsuari.getBlocked());
    }

    @Test
    public void testGetUsuari() {
        String userTest = "prueba";
        UsuariDTO expectedUsuari = createSampleUsuariDTO();

        when(usuariRepository.getUsuari(userTest)).thenReturn(expectedUsuari);

        UsuariDTO result = controller.getUsuari(userTest);
        assertNotNull(result);

        // Comprueba que los parámetros devueltos coinciden con los parámetros proporcionados
        assertEquals(expectedUsuari, result);
    }

    @Test
    public void testLogin() {
        String username = "usuario123";
        String password = "password";
        String email = "email@example.com";
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", username);
        loginRequest.put("password", password);
        loginRequest.put("email", email);

        UsuariDTO expectedUsuari = createSampleUsuariDTO();
        when(usuariRepository.usuariCorrecte(username, password, email)).thenReturn(expectedUsuari);

        UsuariDTO result = controller.login(loginRequest);
        assertNotNull(result);
        assertEquals(expectedUsuari, result);
    }

    @Test
    public void testSignup() {
        String username = "usuario123";
        String password = "password";
        String email = "email@example.com";
        String name = "Nombre";
        String age = "25";
        String telefon = "123456789";
        String language = "es";
        String profileImageUrl = "path/to/profile";

        Map<String, String> signupRequest = new HashMap<>();
        signupRequest.put("username", username);
        signupRequest.put("password", password);
        signupRequest.put("email", email);
        signupRequest.put("name", name);
        signupRequest.put("age", age);
        signupRequest.put("telefon", telefon);
        signupRequest.put("language", language);
        signupRequest.put("profileImageUrl", profileImageUrl);

        when(usuariRepository.crearUsuari(username, password, email, name, telefon, age, language, profileImageUrl, false)).thenReturn("ok");

        String result = controller.signup(signupRequest);
        assertEquals("ok", result);
    }

    @Test
    public void testRemove() {
        String username = "usuario123";
        Map<String, String> removeRequest = new HashMap<>();
        removeRequest.put("username", username);

        when(usuariRepository.usuariRemove(username)).thenReturn(true);

        boolean result = controller.remove(removeRequest);
        assertTrue(result);
    }

    @Test
    public void testToggleBlock() {
        String username = "usuario123";
        boolean isBlocked = true;
        Map<String, Object> blockRequest = new HashMap<>();
        blockRequest.put("username", username);
        blockRequest.put("isBlocked", isBlocked);

        when(usuariRepository.toggleUserBlock(username, isBlocked)).thenReturn(true);

        boolean result = controller.toggleBlock(blockRequest);
        assertTrue(result);
    }

    @Test
    public void testRecover() {
        String email = "email@example.com";
        Map<String, String> recoverRequest = new HashMap<>();
        recoverRequest.put("email", email);

        when(usuariRepository.updatePass(email)).thenReturn("newpassword");
        when(mailService.recoverPass(email, "newpassword")).thenReturn("ok");

        String result = controller.recover(recoverRequest);
        assertEquals("ok", result);
    }

    private UsuariDTO createSampleUsuariDTO() {
        return new UsuariDTO("user1", "Usuario 1", 25, 123456789, "user1@example.com", "password", "es", "path/to/fotoPerfil", false, "location", false);
    }
}
