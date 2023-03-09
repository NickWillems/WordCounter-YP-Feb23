package nl.ordina.ypfeb23.wordcounter.service;


import lombok.RequiredArgsConstructor;
import nl.ordina.ypfeb23.wordcounter.repository.LoggingRepository;
import nl.ordina.ypfeb23.wordcounter.repository.entity.HistoryLoggingEntity;
import org.springframework.stereotype.Service;

import java.util.Date;


@RequiredArgsConstructor
@Service
public class HistoryLogger {
    private final LoggingRepository loggingRepository;

    public void logEvent(final String request, final Object input, final Object output) {
        final HistoryLoggingEntity loggingEntity = new HistoryLoggingEntity(new Date(), request, input, output);
        loggingRepository.save(loggingEntity);
    }
}
