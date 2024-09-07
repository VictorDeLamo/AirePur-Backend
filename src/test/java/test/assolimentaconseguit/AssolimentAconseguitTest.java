package test.assolimentaconseguit;

import com.airepur.controller.AssolimentAconseguitController;
import com.airepur.domain.AssolimentAconseguitDTO;
import com.airepur.service.AssolimentAconseguitService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AssolimentAconseguitTest {

    private MockMvc mockMvc;

    @Mock
    private AssolimentAconseguitService assolimentAconseguitService;

    @InjectMocks
    private AssolimentAconseguitController assolimentAconseguitController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assolimentAconseguitController).build();
    }

    @Test
    public void testGetAssolimentAconseguit() throws Exception {
        ArrayList<AssolimentAconseguitDTO> assoliments = new ArrayList<>();
        // Agrega algunos datos de prueba al mock
        assoliments.add(new AssolimentAconseguitDTO("user1", "Aficionat"));
        assoliments.add(new AssolimentAconseguitDTO("user2", "Principiant"));
        // Configura el mock para devolver los datos simulados
        when(assolimentAconseguitService.getAssoliments()).thenReturn(assoliments);

        mockMvc.perform(get("/assolimentsaconseguits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(Matchers.greaterThan(0)));
    }

    @Test
    public void testGetAssoliment() throws Exception {
        AssolimentAconseguitDTO assoliment = new AssolimentAconseguitDTO("user1", "Aficionat");
        // Configura el mock para devolver el dato simulado
        when(assolimentAconseguitService.getAssoliment("cuenta2", "Novell")).thenReturn(assoliment);

        mockMvc.perform(get("/assolimentsaconseguits/cuenta2/Novell")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(Matchers.greaterThan(0)));
    }

    @Test
    public void testGetAssolimentsUsuari() throws Exception {
        ArrayList<AssolimentAconseguitDTO> assoliments = new ArrayList<>();
        // Agrega algunos datos de prueba al mock
        assoliments.add(new AssolimentAconseguitDTO("cuenta2", "Aficionat"));
        assoliments.add(new AssolimentAconseguitDTO("cuenta2", "Principiant"));
        // Configura el mock para devolver los datos simulados
        when(assolimentAconseguitService.getAssolimentsUsuari("cuenta2")).thenReturn(assoliments);

        mockMvc.perform(get("/assolimentsaconseguits/cuenta2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(Matchers.greaterThan(0)));
    }

    @Test
    public void testGetAssolimentsListUsuari() throws Exception {
        int[] assolimentsList = {1, 0, 1, 0, 1};
        // Configura el mock para devolver el array simulado
        when(assolimentAconseguitService.getAssolimentsListUsuari("cuenta2")).thenReturn(assolimentsList);

        mockMvc.perform(get("/assolimentsaconseguits/assolimentslist/cuenta2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(Matchers.greaterThan(0)));
    }
}
