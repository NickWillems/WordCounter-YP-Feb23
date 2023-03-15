package nl.ordina.ypfeb23.wordcounter.controller;


import nl.ordina.ypfeb23.wordcounter.service.HistoryLogger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


//@Profile("test")
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
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
//        var request = MockMvcRequestBuilders.post("/api/highest-frequency");
//        request.content("input text");
        this.mockMvc.perform(get("/api/test"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
