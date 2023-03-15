package nl.ordina.ypfeb23.wordcounter.controller;


import nl.ordina.ypfeb23.wordcounter.repository.LoggingRepository;
import nl.ordina.ypfeb23.wordcounter.service.HistoryLogger;
import org.hibernate.sql.results.graph.Initializer;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;


@Profile("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WordCounterControllerIntegrationTest {
//    @Autowired
//    private WebApplicationContext webApplicationContext;

    @Mock
    HistoryLogger historyLogger;

    @Autowired
    private MockMvc mockMvc;

//    @Before
//    public void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }

//    @Autowired
//    @InjectMocks
//    private WordCounterController testSubject;

    @Test
    void test() throws Exception {
        System.out.println();
        var request = MockMvcRequestBuilders.get("/api/highest-frequency");
        request.content("input text");
        this.mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
