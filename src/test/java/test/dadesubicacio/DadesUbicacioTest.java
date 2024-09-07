package test.dadesubicacio;

import com.airepur.BackendApplication;
import com.airepur.controller.DadesUbicacioController;
import com.airepur.domain.DadesUbicacioDTO;
import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.UsuariDTO;
import com.airepur.repository.DadesUbicacioRepository;
import com.airepur.service.DadesUbicacioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendApplication.class)
public class DadesUbicacioTest {

    @InjectMocks
    private DadesUbicacioController controller;

    @Spy
    private DadesUbicacioService dadesUbicacioService;

    @MockBean
    private DadesUbicacioRepository dadesUbicacioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Asegurar que DadesUbicacioService utiliza el mock de DadesUbicacioRepository
        dadesUbicacioService.setDadesUbicacioRepository(dadesUbicacioRepository);
    }

    @Test
    void testGetDadesUbicacions() throws SQLException {
        ArrayList<DadesUbicacioDTO> dadesUbicacioDTOS = new ArrayList<>();
        DadesUbicacioDTO sampleDadesUbicacio = createSampleDadesUbicacioDTO();
        dadesUbicacioDTOS.add(sampleDadesUbicacio);

        when(dadesUbicacioRepository.getDadesUbicacions()).thenReturn(dadesUbicacioDTOS);

        ArrayList<DadesUbicacioDTO> result = controller.getDadesUbicacions();
        assertNotNull(result);
        assertEquals(dadesUbicacioDTOS.size(), result.size());

        DadesUbicacioDTO resultDadesUbicacio = result.get(0);
        assertEquals(sampleDadesUbicacio.getUsuari().getUsername(), resultDadesUbicacio.getUsuari().getUsername());
        assertEquals(sampleDadesUbicacio.getUsuari().getPwd(), resultDadesUbicacio.getUsuari().getPwd());
        assertEquals(sampleDadesUbicacio.getUsuari().getLocalitzacio(), resultDadesUbicacio.getUsuari().getLocalitzacio());
        assertEquals(sampleDadesUbicacio.getQualitatAire(), resultDadesUbicacio.getQualitatAire());
        assertEquals(sampleDadesUbicacio.getLocalitzacio().getCodiEstacio(), resultDadesUbicacio.getLocalitzacio().getCodiEstacio());
        assertEquals(sampleDadesUbicacio.getLocalitzacio().getMunicipi(), resultDadesUbicacio.getLocalitzacio().getMunicipi());
        assertEquals(sampleDadesUbicacio.getMunicipi(), resultDadesUbicacio.getMunicipi());
    }

    @Test
    void testGetInfoData() {
        DadesUbicacioDTO dadesUbicacioDTO = createSampleDadesUbicacioDTO();

        when(dadesUbicacioRepository.getInfoData("prueba", "2024-04-20")).thenReturn(dadesUbicacioDTO);

        DadesUbicacioDTO result = controller.getInfoData("prueba", "2024-04-20");

        assertEquals("prueba", result.getUsuari().getUsername());
        assertEquals(1, result.getQualitatAire());
        assertEquals("08205002", result.getLocalitzacio().getCodiEstacio());
        assertEquals("2024-04-20", result.getFecha());
        assertEquals("19:00:00", result.getHora());
        assertEquals("Sant Cugat del Vallès", result.getMunicipi());
    }

    @Test
    void testGetDadesUbicacioHistoric() {
        String username = "prueba";
        ArrayList<DadesUbicacioDTO> historicData = new ArrayList<>();
        DadesUbicacioDTO dadesUbicacioDTO = createSampleDadesUbicacioDTO();
        historicData.add(dadesUbicacioDTO);

        when(dadesUbicacioRepository.getDadesUbicacioHistoric(username)).thenReturn(historicData);

        ArrayList<DadesUbicacioDTO> result = controller.getDadesUbicacioHistoric(username);

        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertEquals(username, result.get(0).getUsuari().getUsername());
    }

    @Test
    public void testSaveDadesUbicacio() throws SQLException {
        Map<String, String> dadesUbicacioRequest = new HashMap<>();
        dadesUbicacioRequest.put("longitud", "2.088998317718506");
        dadesUbicacioRequest.put("latitud", "41.47681427001953");
        dadesUbicacioRequest.put("municipi", "Sant Cugat del Vallès");
        dadesUbicacioRequest.put("username", "prueba");
        dadesUbicacioRequest.put("fecha", "2024-04-20");
        dadesUbicacioRequest.put("hora", "19:00:00");

        // Mocking the repository method called by the service
        when(dadesUbicacioRepository.saveDadesUbicacio(any(), any(), any(), any(), any(), any()))
                .thenReturn(true);

        // Mocking the service method to ensure it returns the expected value
        when(dadesUbicacioService.saveDadesUbicacio(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);

        boolean result = controller.saveDadesUbicacio(dadesUbicacioRequest);

        assertTrue(result);
    }

    private DadesUbicacioDTO createSampleDadesUbicacioDTO() {
        UsuariDTO usuari = new UsuariDTO("prueba", "nom", 25, 123456789, "email@example.com", "password", "es", "fotoPerfil", false, "localitzacio", false);
        LocalitzacioEstacioDTO localitzacio = new LocalitzacioEstacioDTO("08205002", "Sant Cugat del Vallès");

        DadesUbicacioDTO dadesUbicacioDTO = new DadesUbicacioDTO(usuari, localitzacio, "2024-04-20", "19:00:00");
        dadesUbicacioDTO.setQualitatAire(1);
        dadesUbicacioDTO.setMunicipi("Sant Cugat del Vallès");

        return dadesUbicacioDTO;
    }

}
