package test.planta;

import com.airepur.BackendApplication;
import com.airepur.controller.PlantaController;
import com.airepur.domain.PlantaDTO;
import com.airepur.domain.NivellDTO;
import com.airepur.domain.UsuariDTO;
import com.airepur.repository.PlantaRepository;
import com.airepur.service.PlantaService;
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
public class PlantaTest {

    @InjectMocks
    private PlantaController controller;

    @Spy
    private PlantaService plantaService;

    @MockBean
    private PlantaRepository plantaRepository;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Asegurar que PlantaService utiliza el mock de PlantaRepository
        plantaService.setPlantaRepository(plantaRepository);

        // Configuración del mock de la conexión y el statement
        Method getConnectionMethod = PlantaRepository.class.getDeclaredMethod("getConnection");
        getConnectionMethod.setAccessible(true);
        when(getConnectionMethod.invoke(plantaRepository)).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void testGetPlantes() throws SQLException {
        ArrayList<PlantaDTO> plantes = new ArrayList<>();
        PlantaDTO samplePlanta = createSamplePlantaDTO();
        plantes.add(samplePlanta);

        when(plantaRepository.getPlantes()).thenReturn(plantes);

        ArrayList<PlantaDTO> result = controller.getPlantes();
        assertNotNull(result);
        assertEquals(plantes.size(), result.size());

        PlantaDTO resultPlanta = result.get(0);
        assertEquals(samplePlanta.getLastAnswer(), resultPlanta.getLastAnswer());
        assertEquals(samplePlanta.getPuntuacioAlNivell(), resultPlanta.getPuntuacioAlNivell());
        assertEquals(samplePlanta.getPuntuacioTotal(), resultPlanta.getPuntuacioTotal());
        assertEquals(samplePlanta.getNivell().getNivell(), resultPlanta.getNivell().getNivell());
        assertEquals(samplePlanta.getNivell().getPuntuacioNivell(), resultPlanta.getNivell().getPuntuacioNivell());
        assertEquals(samplePlanta.getUsuari().getUsername(), resultPlanta.getUsuari().getUsername());
        assertEquals(samplePlanta.getUsuari().getNom(), resultPlanta.getUsuari().getNom());
        assertEquals(samplePlanta.getUsuari().getEdat(), resultPlanta.getUsuari().getEdat());
        assertEquals(samplePlanta.getUsuari().getTlf(), resultPlanta.getUsuari().getTlf());
        assertEquals(samplePlanta.getUsuari().getEmail(), resultPlanta.getUsuari().getEmail());
        assertEquals(samplePlanta.getUsuari().getPwd(), resultPlanta.getUsuari().getPwd());
        assertEquals(samplePlanta.getUsuari().getIdioma(), resultPlanta.getUsuari().getIdioma());
        assertEquals(samplePlanta.getUsuari().getFotoPerfil(), resultPlanta.getUsuari().getFotoPerfil());
        assertEquals(samplePlanta.getUsuari().getAdministrador(), resultPlanta.getUsuari().getAdministrador());
        assertEquals(samplePlanta.getUsuari().getLocalitzacio(), resultPlanta.getUsuari().getLocalitzacio());
        assertEquals(samplePlanta.getUsuari().getBlocked(), resultPlanta.getUsuari().getBlocked());
        assertEquals(samplePlanta.getConsecutivedays(), resultPlanta.getConsecutivedays());
    }

    @Test
    public void testGetPlantaUsuari() {
        String userTest = "prueba";
        PlantaDTO expectedPlanta = createSamplePlantaDTO();

        when(plantaRepository.getPlantaUsuari(userTest)).thenReturn(expectedPlanta);

        PlantaDTO result = controller.getPlantaUsuari(userTest);
        assertNotNull(result);

        // Comprueba que los parámetros devueltos coinciden con los parámetros proporcionados
        assertEquals(expectedPlanta, result);
    }

    @Test
    public void testUpdateDataPlanta() {
        String userTest = "usuario123";
        Integer puntuacio = 20;
        Map<String, String> plantaRequest = new HashMap<>();
        plantaRequest.put("puntuacio", puntuacio.toString());

        when(plantaRepository.updateDataPlanta(userTest, puntuacio)).thenReturn("ok");

        String result = controller.updateDataPlanta(userTest, plantaRequest);
        assertEquals("ok", result);
    }

    private PlantaDTO createSamplePlantaDTO() {
        NivellDTO nivell = new NivellDTO(1, 100);

        UsuariDTO usuari = new UsuariDTO("user1", "Usuario 1", 25, 123456789, "user1@example.com", "password", "es", "path/to/fotoPerfil", false, "location", false);

        return new PlantaDTO("lastAnswer", 10, 100, nivell, usuari, 5);
    }
}
