package test.ranking;

import com.airepur.controller.RankingController;
import com.airepur.domain.InfoComunitatDTO;
import com.airepur.domain.RankingComunitatDTO;
import com.airepur.domain.RankingUsuariDTO;
import com.airepur.service.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class Rankingtest {

    private MockMvc mockMvc;

    @Mock
    private RankingService rankingService;

    @InjectMocks
    private RankingController rankingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rankingController).build();
    }

    @Test
    public void testRankingUsers() throws Exception {
        ArrayList<RankingUsuariDTO> mockRanking = new ArrayList<>();
        // Agrega algunos datos de prueba al mock
        mockRanking.add(new RankingUsuariDTO("user1", 100, 1));
        mockRanking.add(new RankingUsuariDTO("user2", 200, 2));

        when(rankingService.rankingUsers()).thenReturn(mockRanking);

        // Ejecuta la función que se va a probar
        mockMvc.perform(post("/ranking/usuaris")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].puntuacionTotal").value(100))
                .andExpect(jsonPath("$[0].posicion").value(1))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].puntuacionTotal").value(200))
                .andExpect(jsonPath("$[1].posicion").value(2));

        // Verifica que el servicio haya sido invocado correctamente
        verify(rankingService, times(1)).rankingUsers();
    }

    @Test
    public void testRankingComunitats() throws Exception {
        ArrayList<RankingComunitatDTO> mockRanking = new ArrayList<>();
        // Agrega algunos datos de prueba al mock
        mockRanking.add(new RankingComunitatDTO("Barcelona", 500));
        mockRanking.add(new RankingComunitatDTO("Madrid", 300));

        when(rankingService.rankingComunitats()).thenReturn(mockRanking);

        // Ejecuta la función que se va a probar
        mockMvc.perform(get("/ranking/comunitats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].municipi").value("Barcelona"))
                .andExpect(jsonPath("$[0].puntuacion").value(500))
                .andExpect(jsonPath("$[1].municipi").value("Madrid"))
                .andExpect(jsonPath("$[1].puntuacion").value(300));

        // Verifica que el servicio haya sido invocado correctamente
        verify(rankingService, times(1)).rankingComunitats();
    }

    @Test
    public void testInfoComunitat() throws Exception {
        ArrayList<InfoComunitatDTO> mockInfo = new ArrayList<>();
        // Agrega algunos datos de prueba al mock
        mockInfo.add(new InfoComunitatDTO("123ABC", "Barcelona", 41.388790f, 2.158990f, 8));
        mockInfo.add(new InfoComunitatDTO("456DEF", "Madrid", 40.416775f, -3.703790f, 7));

        when(rankingService.infoComunitat()).thenReturn(mockInfo);

        // Ejecuta la función que se va a probar
        mockMvc.perform(get("/ranking/infoComunitat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].codi").value("123ABC"))
                .andExpect(jsonPath("$[0].municipi").value("Barcelona"))
                .andExpect(jsonPath("$[0].longitud").value(41.388790f))
                .andExpect(jsonPath("$[0].latitud").value(2.158990f))
                .andExpect(jsonPath("$[0].avgNivell").value(8))
                .andExpect(jsonPath("$[1].codi").value("456DEF"));
    }
}

