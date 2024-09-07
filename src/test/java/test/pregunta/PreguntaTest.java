package test.pregunta;

import com.airepur.BackendApplication;
import com.airepur.controller.PreguntaController;
import com.airepur.domain.PreguntaDTO;
import com.airepur.repository.PreguntaRepository;
import com.airepur.service.PreguntaService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendApplication.class)
public class PreguntaTest {

    @InjectMocks
    private PreguntaController controller;

    @Spy
    private PreguntaService preguntaService;

    @MockBean
    private PreguntaRepository preguntaRepository;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Asegurar que PreguntaService utiliza el mock de PreguntaRepository
        preguntaService.setPreguntaRepository(preguntaRepository);

        // Configuración del mock de la conexión y el statement
        Method getConnectionMethod = PreguntaRepository.class.getDeclaredMethod("getConnection");
        getConnectionMethod.setAccessible(true);
        when(getConnectionMethod.invoke(preguntaRepository)).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void testGetPreguntes() throws SQLException {
        String idioma = "es";
        ArrayList<PreguntaDTO> preguntes = new ArrayList<>();
        PreguntaDTO samplePregunta = createSamplePreguntaDTO();
        preguntes.add(samplePregunta);

        when(preguntaRepository.getPreguntes(idioma)).thenReturn(preguntes);

        ArrayList<PreguntaDTO> result = controller.getPreguntes(idioma);

        assertNotNull(result);
        assertEquals(preguntes.size(), result.size());

        PreguntaDTO resultPregunta = result.get(0);
        assertEquals(samplePregunta.getNumP(), resultPregunta.getNumP());
        assertEquals(samplePregunta.getContingut(), resultPregunta.getContingut());
        assertEquals(samplePregunta.getCorrecta(), resultPregunta.getCorrecta());
        assertEquals(samplePregunta.getOpcioA(), resultPregunta.getOpcioA());
        assertEquals(samplePregunta.getOpcioB(), resultPregunta.getOpcioB());
        assertEquals(samplePregunta.getOpcioC(), resultPregunta.getOpcioC());
        assertEquals(samplePregunta.getOpcioD(), resultPregunta.getOpcioD());
    }

    @Test
    public void testGetPregunta() throws SQLException {
        Integer nump = 1;
        PreguntaDTO expectedPregunta = createSamplePreguntaDTO();

        when(preguntaRepository.getPregunta(nump)).thenReturn(expectedPregunta);

        PreguntaDTO result = controller.getPregunta(nump);
        assertNotNull(result);

        // Comprueba que los parámetros devueltos coinciden con los parámetros proporcionados
        assertEquals(expectedPregunta, result);
    }

    @Test
    public void testEsPreguntaCorrecta() throws SQLException {
        Integer nump = 1;
        Integer respCorrecta = 4;
        Integer respIncorrecta = 2;

        when(preguntaRepository.esPreguntaCorrecta(nump, respCorrecta)).thenReturn(true);
        when(preguntaRepository.esPreguntaCorrecta(nump, respIncorrecta)).thenReturn(false);

        assertTrue(controller.esPreguntaCorrecta(nump, respCorrecta));
        assertFalse(controller.esPreguntaCorrecta(nump, respIncorrecta));
    }

    @Test
    public void testGetPreguntesRandoms() throws SQLException {
        String idioma = "es";
        ArrayList<PreguntaDTO> preguntes = new ArrayList<>();
        preguntes.add(createSamplePreguntaDTO());
        preguntes.add(createSamplePreguntaDTO());
        preguntes.add(createSamplePreguntaDTO());

        when(preguntaRepository.getPreguntesRandoms(idioma)).thenReturn(preguntes);

        ArrayList<PreguntaDTO> result = controller.getPreguntesRandoms(idioma);
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    private PreguntaDTO createSamplePreguntaDTO() {
        return new PreguntaDTO(1, "¿Qué mide el índice de calidad del aire (AQI)?", "4", "Niveles de agua potable", "Niveles de radiación solar", "Niveles de ruido", "es");
    }
}
