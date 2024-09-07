package test.publicaciotest;

import com.airepur.controller.PublicacioController;
import com.airepur.domain.PublicacioDTO;
import com.airepur.service.PublicacioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PublicacioTest {

    private MockMvc mockMvc;

    @Mock
    private PublicacioService publicacioService;

    @InjectMocks
    private PublicacioController publicacioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(publicacioController).build();
    }

    @Test
    public void testGetPublicacions() throws Exception {
        ArrayList<PublicacioDTO> expectedList = new ArrayList<>();
        expectedList.add(new PublicacioDTO("id1", "Contenido", "image.jpg", "2024-05-10", "12:00", null, "urlAvatar"));

        when(publicacioService.getPublicacions()).thenReturn(expectedList);

        mockMvc.perform(get("/publicacions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPublicacio").value("id1"))
                .andExpect(jsonPath("$[0].contingut").value("Contenido"));

        verify(publicacioService).getPublicacions();
    }

    @Test
    public void testGetPublicacio() throws Exception {
        PublicacioDTO expectedPublicacio = new PublicacioDTO("id1", "Contenido", "image.jpg", "2024-05-10", "12:00", null, "urlAvatar");

        when(publicacioService.getPublicacio("id1")).thenReturn(expectedPublicacio);

        mockMvc.perform(get("/publicacions/{id}", "id1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPublicacio").value("id1"))
                .andExpect(jsonPath("$.contingut").value("Contenido"));

        verify(publicacioService).getPublicacio("id1");
    }

    @Test
    public void testSavePublicacio() throws Exception {
        Map<String, String> publicacioRequest = new HashMap<>();
        publicacioRequest.put("username", "userTest");
        publicacioRequest.put("contingut", "Content");
        publicacioRequest.put("fecha", "2024-05-10");
        publicacioRequest.put("hora", "12:00");
        publicacioRequest.put("idPublicacio", "id1");
        publicacioRequest.put("idanswered", "id0");
        publicacioRequest.put("urlAvatar", "urlAvatar");
        publicacioRequest.put("imatge", "image.jpg");

        doNothing().when(publicacioService).savePublicacio(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());

        mockMvc.perform(post("/publicacions/postpublicacio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"userTest\",\"contingut\":\"Content\",\"fecha\":\"2024-05-10\",\"hora\":\"12:00\",\"idPublicacio\":\"id1\",\"idanswered\":\"id0\",\"urlAvatar\":\"urlAvatar\",\"imatge\":\"image.jpg\"}"))
                .andExpect(status().isOk());

        verify(publicacioService).savePublicacio("userTest", "Content", "image.jpg", "2024-05-10", "12:00", "id1", "id0", "urlAvatar");
    }


}