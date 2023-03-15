package nl.ordina.ypfeb23.wordcounter.controller;

import nl.ordina.ypfeb23.wordcounter.TestUtil;
import nl.ordina.ypfeb23.wordcounter.repository.LoggingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class WordCounterControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LoggingRepository loggingRepository;

    @Test
    void shouldPerformRequest() throws Exception {
        // Arrange
        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/highest-frequency");
        request.content(TestUtil.DEFAULT_TEST_STRING);
        request.header("Content-Type", "text/plain");

        // Act
        final ResultActions resultActions = this.mockMvc.perform(request);

        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.content().string("4"));
    }

    @Test
    void shouldCallLoggingRepository() throws Exception {
        // Arrange
        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/highest-frequency");
        request.content(TestUtil.DEFAULT_TEST_STRING);
        request.header("Content-Type", "text/plain");

        // Act
        final ResultActions resultActions = this.mockMvc.perform(request);

        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(loggingRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void shouldNotPerformRequest_unsupportedMediaType() throws Exception {
        // Arrange
        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/highest-frequency");
        request.content(TestUtil.DEFAULT_TEST_STRING);
        request.header("Content-Type", "audio/wave");

        // Act
        final ResultActions resultActions = this.mockMvc.perform(request);

        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
    }
}
