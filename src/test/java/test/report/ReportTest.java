package test.report;

import com.airepur.controller.ReportController;
import com.airepur.domain.ReportDTO;
import com.airepur.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ReportTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    public void testSaveReport_Success() throws Exception {
        when(reportService.saveReport(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn("ok");

        mockMvc.perform(post("/reports/postreport")
                        .header("Authorization", "Bearer someToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"missatge\": \"Mensaje\", \"fecha\": \"2024-05-29\", \"hora\": \"12:00\", \"usernameReportador\": \"user1\", \"usernameReportat\": \"user2\", \"idpublicacio\": \"publicacion1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));

        verify(reportService, times(1)).saveReport(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    }


}
