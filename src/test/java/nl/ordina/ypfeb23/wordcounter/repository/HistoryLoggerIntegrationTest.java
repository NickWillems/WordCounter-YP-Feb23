package nl.ordina.ypfeb23.wordcounter.repository;


import nl.ordina.ypfeb23.wordcounter.repository.entity.HistoryLoggingEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Profile("tc")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HistoryLoggerIntegrationTest {
    @Autowired
    private LoggingRepository testSubject;

    @BeforeEach
    void beforeEachTest() {
        // Arrange
        testSubject.deleteAll();
    }

    @Test
    @Transactional
    void shouldLogEvent() {
        // Arrange
        final long dateEpoch = 1672531200;
        final HistoryLoggingEntity testEntity = new HistoryLoggingEntity(new Date(dateEpoch), "request", "input", "output");

        // Act
        testSubject.save(testEntity);
        final List<HistoryLoggingEntity> result = testSubject.findAll();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(new Date(dateEpoch), result.get(0).recordedAt);
        Assertions.assertEquals("request", result.get(0).request);
        Assertions.assertEquals("input", result.get(0).input);
        Assertions.assertEquals("output", result.get(0).output);
    }
}
