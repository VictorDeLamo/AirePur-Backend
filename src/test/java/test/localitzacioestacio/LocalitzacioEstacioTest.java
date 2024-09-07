package test.localitzacioestacio;

import com.airepur.BackendApplication;
import com.airepur.controller.LocalitzacioEstacioController;
import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.repository.LocalitzacioEstacioRepository;
import com.airepur.service.LocalitzacioEstacioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendApplication.class)
public class LocalitzacioEstacioTest {

    @InjectMocks
    private LocalitzacioEstacioController controller;

    @Mock
    private LocalitzacioEstacioService localitzacioEstacioService;

    @Mock
    private LocalitzacioEstacioRepository localitzacioEstacioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        localitzacioEstacioService.setLocalitzacioEstacioRepository(localitzacioEstacioRepository);
    }

    @Test
    public void getLocalitzacionsEstacio() throws SQLException {
        ArrayList<LocalitzacioEstacioDTO> localitzacionsEstacioDTOS = new ArrayList<>();
        LocalitzacioEstacioDTO sampleLocalitzacio = createSampleLocalitzacioEstacioDTO();
        localitzacionsEstacioDTOS.add(sampleLocalitzacio);

        when(localitzacioEstacioService.getLocalitzacions()).thenReturn(localitzacionsEstacioDTOS);

        ArrayList<LocalitzacioEstacioDTO> result = controller.getLocalitzacions();
        assertNotNull(result);
        assertEquals(localitzacionsEstacioDTOS.size(), result.size());

        LocalitzacioEstacioDTO resultLocalitzacio = result.get(0);
        assertEquals(sampleLocalitzacio.getCodiEstacio(), resultLocalitzacio.getCodiEstacio());
        assertEquals(sampleLocalitzacio.getComarca(), resultLocalitzacio.getComarca());
        assertEquals(sampleLocalitzacio.getMunicipi(), resultLocalitzacio.getMunicipi());
        assertEquals(sampleLocalitzacio.getLongitud(), resultLocalitzacio.getLongitud());
        assertEquals(sampleLocalitzacio.getLatitud(), resultLocalitzacio.getLatitud());
    }

    @Test
    public void testGetLocalitzacioEstacio() throws SQLException {
        String codiEstacio = "08121013";
        LocalitzacioEstacioDTO localitzacioEstacioDTO = createSampleLocalitzacioEstacioDTO();

        when(localitzacioEstacioService.getLocalitzacio(codiEstacio)).thenReturn(localitzacioEstacioDTO);

        LocalitzacioEstacioDTO result = controller.getLocalitzacio(codiEstacio);
        assertNotNull(result);

        assertEquals(codiEstacio, result.getCodiEstacio());
        assertEquals("Maresme", result.getComarca());
        assertEquals("Mataró", result.getMunicipi());
        assertEquals(2.44329833984375f, result.getLongitud());
        assertEquals(41.547176361083984f, result.getLatitud());
    }

    @Test
    public void testGetCoordenadesLocalitzacioEstacio() throws SQLException {
        Float longitud = 2.44329833984375f;
        Float latitud = 41.547176361083984f;
        LocalitzacioEstacioDTO localitzacioEstacioDTO = createSampleLocalitzacioEstacioDTO();

        when(localitzacioEstacioService.getLocalitzacioCoordenades(longitud, latitud)).thenReturn(localitzacioEstacioDTO);

        LocalitzacioEstacioDTO result = controller.getLocalitzacioCoordenades(longitud, latitud);
        assertNotNull(result);

        assertEquals(localitzacioEstacioDTO.getCodiEstacio(), result.getCodiEstacio());
        assertEquals(localitzacioEstacioDTO.getMunicipi(), result.getMunicipi());
    }

    @Test
    public void testSaveLocalitzacioEstacio() throws SQLException {
        LocalitzacioEstacioDTO localitzacioEstacio = new LocalitzacioEstacioDTO("08015021", "Maresme", "Mataró", 2.44329833984375f, 41.547176361083984f);

        when(localitzacioEstacioService.saveLocalitzacioEstacio(any(LocalitzacioEstacioDTO.class))).thenReturn(localitzacioEstacio);

        LocalitzacioEstacioDTO result = controller.saveLocalitzacioEstacio(localitzacioEstacio);
        assertNotNull(result);

        assertEquals(localitzacioEstacio.getCodiEstacio(), result.getCodiEstacio());
        assertEquals(localitzacioEstacio.getComarca(), result.getComarca());
        assertEquals(localitzacioEstacio.getMunicipi(), result.getMunicipi());
        assertEquals(localitzacioEstacio.getLongitud(), result.getLongitud());
        assertEquals(localitzacioEstacio.getLatitud(), result.getLatitud());
    }

    @Test
    public void testGetLocalitzacioMunicipis() throws SQLException {
        Map<String, String> municipis = new HashMap<>();
        municipis.put("08015021", "Mataró");

        when(localitzacioEstacioService.getLocalitzacioMunicipis()).thenReturn(municipis);

        Map<String, String> result = controller.getLocalitzacioMunicipis();
        assertNotNull(result);
        assertEquals("Mataró", result.get("08015021"));
    }

    private LocalitzacioEstacioDTO createSampleLocalitzacioEstacioDTO() {
        return new LocalitzacioEstacioDTO("08121013", "Maresme", "Mataró", 2.44329833984375f, 41.547176361083984f);
    }
}
