package test.assoliment;

import com.airepur.controller.AssolimentController;
import com.airepur.domain.AssolimentDTO;
        import com.airepur.service.AssolimentService;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;
        import org.springframework.http.MediaType;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.setup.MockMvcBuilders;

        import java.util.ArrayList;
        import java.util.List;

        import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AssolimentTest {


    private MockMvc mockMvc;

    @Mock
    private AssolimentService assolimentService;

    @InjectMocks
    private AssolimentController assolimentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assolimentController).build();
    }

    @Test
    public void testGetAssoliments() throws Exception {
        ArrayList<AssolimentDTO> assoliments = new ArrayList<>();

        when(assolimentService.getAssoliments()).thenReturn(assoliments);

        mockMvc.perform(get("/assoliments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(23))
                .andExpect(jsonPath("$[0].nom").value("Aficionat"))
                .andExpect(jsonPath("$[0].descripcio").value("Has arriabt al nivell 5"))
                .andExpect(jsonPath("$[11].nom").value("50 seguits"))
                .andExpect(jsonPath("$[11].descripcio").value("Respon el Ecoquiz 50 dies seguits"));


    }

    @Test
    public void testGetAssoliment() throws Exception {
        AssolimentDTO assoliment = new AssolimentDTO("Aficionat", "Has arriabt al nivell 5");

        when(assolimentService.getAssoliment("Aficionat")).thenReturn(assoliment);

        mockMvc.perform(get("/assoliments/Aficionat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Aficionat"))
                .andExpect(jsonPath("$.descripcio").value("Has arriabt al nivell 5"));


    }
}
