package test.nivell;

import com.airepur.BackendApplication;
import com.airepur.controller.NivellController;
import com.airepur.domain.NivellDTO;
import com.airepur.repository.NivellRepository;
import com.airepur.service.NivellService;
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
public class NivellTest {

    @InjectMocks
    private NivellController controller;

    @Spy
    private NivellService nivellService;

    @MockBean
    private NivellRepository nivellRepository;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Asegurar que NivellService utiliza el mock de NivellRepository
        nivellService.setNivellRepository(nivellRepository);

        // Configuración del mock de la conexión y el statement
        Method getConnectionMethod = NivellRepository.class.getDeclaredMethod("getConnection");
        getConnectionMethod.setAccessible(true);
        when(getConnectionMethod.invoke(nivellRepository)).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void testGetNivells() throws SQLException {
        ArrayList<NivellDTO> nivells = new ArrayList<>();
        NivellDTO sampleNivell = new NivellDTO(1, 100);
        nivells.add(sampleNivell);

        when(nivellRepository.getNivells()).thenReturn(nivells);

        ArrayList<NivellDTO> result = controller.getNivells();
        assertNotNull(result);
        assertEquals(nivells.size(), result.size());

        NivellDTO resultNivell = result.get(0);
        assertEquals(sampleNivell.getNivell(), resultNivell.getNivell());
        assertEquals(sampleNivell.getPuntuacioNivell(), resultNivell.getPuntuacioNivell());
    }

    @Test
    public void testGetNivell() {
        Integer nivell = 1;
        NivellDTO expectedNivell = new NivellDTO(nivell, 100);

        when(nivellRepository.getNivell(nivell)).thenReturn(expectedNivell);

        NivellDTO result = controller.getNivell(nivell);
        assertNotNull(result);

        // Comprueba que los parámetros devueltos coinciden con los parámetros proporcionados
        assertEquals(expectedNivell.getNivell(), result.getNivell());
        assertEquals(expectedNivell.getPuntuacioNivell(), result.getPuntuacioNivell());
    }
}
