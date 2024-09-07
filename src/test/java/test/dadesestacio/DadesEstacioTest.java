package test.dadesestacio;

import com.airepur.BackendApplication;
import com.airepur.controller.DadesEstacioController;
import com.airepur.domain.DadesEstacioDTO;
import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.SuggestedPlaceDTO;
import com.airepur.repository.DadesEstacioRepository;
import com.airepur.service.DadesEstacioService;
import org.json.JSONException;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendApplication.class)
public class DadesEstacioTest {

    @InjectMocks
    private DadesEstacioController controller;

    @Spy
    private DadesEstacioService dadesEstacioService;

    @MockBean
    private DadesEstacioRepository dadesEstacioRepository;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Asegurar que DadesEstacioService utiliza el mock de DadesEstacioRepository
        dadesEstacioService.setDadesEstacioRepository(dadesEstacioRepository);

        // Configuración del mock de la conexión y el statement
        Method getConnectionMethod = DadesEstacioRepository.class.getDeclaredMethod("getConnection");
        getConnectionMethod.setAccessible(true);
        when(getConnectionMethod.invoke(dadesEstacioRepository)).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void testGetDadesEstacions() throws SQLException {
        ArrayList<DadesEstacioDTO> dadesEstacioDTOS = new ArrayList<>();
        DadesEstacioDTO sampleDadesEstacio = createSampleDadesEstacioDTO();
        dadesEstacioDTOS.add(sampleDadesEstacio);

        when(dadesEstacioRepository.getDadesEstacions()).thenReturn(dadesEstacioDTOS);

        ArrayList<DadesEstacioDTO> result = controller.getDadesEstacions();
        assertNotNull(result);
        assertEquals(dadesEstacioDTOS.size(), result.size());

        DadesEstacioDTO resultDadesEstacio = result.get(0);
        assertEquals(sampleDadesEstacio.getLocalitzacioEstacioDTO().getCodiEstacio(), resultDadesEstacio.getLocalitzacioEstacioDTO().getCodiEstacio());
        assertEquals(sampleDadesEstacio.getLocalitzacioEstacioDTO().getMunicipi(), resultDadesEstacio.getLocalitzacioEstacioDTO().getMunicipi());
        assertEquals(sampleDadesEstacio.getFecha(), resultDadesEstacio.getFecha());
        assertEquals(sampleDadesEstacio.getContaminant(), resultDadesEstacio.getContaminant());
        assertEquals(sampleDadesEstacio.getUnitat(), resultDadesEstacio.getUnitat());
    }

    @Test
    public void testGetDadesEstacioPK() {
        String codiEstacio = "08121013";
        String fecha = "2024-02-23T00:00:00.000";
        String contaminant = "O3";

        LocalitzacioEstacioDTO localitzacioEstacioDTO = new LocalitzacioEstacioDTO(codiEstacio, "Some Location");
        DadesEstacioDTO dadesEstacioDTO = new DadesEstacioDTO(localitzacioEstacioDTO, fecha, contaminant, "µg/m3");

        when(dadesEstacioRepository.getDadesEstacioPK(codiEstacio, fecha, contaminant)).thenReturn(dadesEstacioDTO);

        DadesEstacioDTO result = controller.getDadesEstacioPK(codiEstacio, fecha, contaminant);
        assertNotNull(result);

        // Comprueba que los parámetros devueltos coinciden con los parámetros proporcionados
        assertEquals(codiEstacio, result.getLocalitzacioEstacioDTO().getCodiEstacio());
        assertEquals(fecha, result.getFecha());
        assertEquals(contaminant, result.getContaminant());
        assertEquals("Some Location", result.getLocalitzacioEstacioDTO().getMunicipi());
        assertEquals("µg/m3", result.getUnitat());
    }

    @Test
    public void testGetDadesEstacioLocalitzacioAvui() throws SQLException {
        String codiEstacio = "08121013";
        ArrayList<DadesEstacioDTO> dadesEstacioDTOS = new ArrayList<>();
        DadesEstacioDTO sampleDadesEstacio = createSampleDadesEstacioDTO();
        dadesEstacioDTOS.add(sampleDadesEstacio);

        when(dadesEstacioRepository.getDadesEstacioLocalitzacioAvui(codiEstacio)).thenReturn(dadesEstacioDTOS);

        ArrayList<DadesEstacioDTO> result = controller.getDadesEstacioLocalitzacioAvui(codiEstacio);
        assertNotNull(result);
        assertEquals(dadesEstacioDTOS.size(), result.size());
        for (DadesEstacioDTO dadesEstacioDTO : result) {
            assertEquals(codiEstacio, dadesEstacioDTO.getLocalitzacioEstacioDTO().getCodiEstacio());
        }
    }

    @Test
    public void testGetDadesEstacioLocalitzacioDesdeData() throws SQLException {
        String codiEstacio = "08121013";
        String fecha = "2024-02-23T00:00:00.000";
        String fechaActualString = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00:00.000";
        ArrayList<DadesEstacioDTO> dadesEstacioDTOS = new ArrayList<>();
        DadesEstacioDTO sampleDadesEstacio = createSampleDadesEstacioDTO();
        dadesEstacioDTOS.add(sampleDadesEstacio);

        when(dadesEstacioRepository.getDadesEstacioLocalitzacioDesdeData(codiEstacio, fecha, "O3")).thenReturn(dadesEstacioDTOS);

        ArrayList<DadesEstacioDTO> result = controller.getDadesEstacioLocalitzacioDesdeData(codiEstacio, fecha, "O3");

        assertNotNull(result);
        for (DadesEstacioDTO dadesEstacioDTO : result) {
            String fechaConsultaString = dadesEstacioDTO.getFecha();
            assertTrue(fechaConsultaString.compareTo(fecha) >= 0 && fechaConsultaString.compareTo(fechaActualString) <= 0);
            assertEquals(codiEstacio, dadesEstacioDTO.getLocalitzacioEstacioDTO().getCodiEstacio());
        }
    }

    @Test
    public void testGetICAactual() throws SQLException {
        String codiEstacio = "08015021";
        Integer expectedIca = 100;

        when(dadesEstacioRepository.getICAactual(codiEstacio)).thenReturn(expectedIca);

        Integer result = controller.getICAactual(codiEstacio);
        assertNotNull(result);
        assertEquals(expectedIca, result);
    }

    @Test
    public void testGetSuggestedPlaces() throws SQLException, JSONException {
        float longitud = 2.357787f;
        float latitud = 41.500810f;
        ArrayList<SuggestedPlaceDTO> suggestedPlaces = new ArrayList<>();
        SuggestedPlaceDTO samplePlace = new SuggestedPlaceDTO("Santa Coloma de Gramenet", 75, 12.5f);
        suggestedPlaces.add(samplePlace);

        when(dadesEstacioRepository.getSuggestedplaces(longitud, latitud)).thenReturn(suggestedPlaces);

        ArrayList<SuggestedPlaceDTO> result = controller.getSuggestedPlaces(longitud, latitud);
        assertNotNull(result);
        assertEquals(suggestedPlaces.size(), result.size());
        SuggestedPlaceDTO resultPlace = result.get(0);
        assertEquals(samplePlace.getMunicipi(), resultPlace.getMunicipi());
        assertEquals(samplePlace.getICA(), resultPlace.getICA());
        assertEquals(samplePlace.getDistancia(), resultPlace.getDistancia());
    }

    @Test
    public void testSaveDadesEstacio() {
        LocalitzacioEstacioDTO localitzacioEstacioDTO = new LocalitzacioEstacioDTO("08015021", "Mataró");
        DadesEstacioDTO dadesEstacio = new DadesEstacioDTO(localitzacioEstacioDTO, "2024-03-18T00:00:00.000", "O3", "µg/m3");

        when(dadesEstacioRepository.saveDadesEstacio(any(DadesEstacioDTO.class))).thenReturn(dadesEstacio);

        DadesEstacioDTO result = controller.saveDadesEstacio(dadesEstacio);
        assertNotNull(result);

        assertEquals(dadesEstacio.getLocalitzacioEstacioDTO().getCodiEstacio(), result.getLocalitzacioEstacioDTO().getCodiEstacio());
        assertEquals(dadesEstacio.getLocalitzacioEstacioDTO().getMunicipi(), result.getLocalitzacioEstacioDTO().getMunicipi());
        assertEquals(dadesEstacio.getFecha(), result.getFecha());
        assertEquals(dadesEstacio.getContaminant(), result.getContaminant());
        assertEquals(dadesEstacio.getUnitat(), result.getUnitat());
    }

    private DadesEstacioDTO createSampleDadesEstacioDTO() {
        LocalitzacioEstacioDTO localitzacioEstacioDTO = new LocalitzacioEstacioDTO("08121013", "Some Location");
        return new DadesEstacioDTO(localitzacioEstacioDTO, "2024-02-23T00:00:00.000", "O3", "µg/m3");
    }
}
