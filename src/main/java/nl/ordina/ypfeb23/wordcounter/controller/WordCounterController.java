package nl.ordina.ypfeb23.wordcounter.controller;

import lombok.RequiredArgsConstructor;
import nl.ordina.ypfeb23.wordcounter.service.HistoryLogger;
import nl.ordina.ypfeb23.wordcounter.service.WordFrequency;
import nl.ordina.ypfeb23.wordcounter.service.WordFrequencyAnalyzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController("/api")
public class WordCounterController {
    private final WordFrequencyAnalyzer wordFrequencyAnalyzer;
    private final HistoryLogger historyLogger;

    @PostMapping(
            path = "/highest-frequency",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> calculateHighestFrequency(@RequestBody final String inputText) {
        final Integer wordWithHighestFrequency = wordFrequencyAnalyzer.calculateHighestFrequency(inputText);
        historyLogger.logEvent("/highest-frequency", inputText, wordWithHighestFrequency);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(wordWithHighestFrequency);
    }

    @PostMapping(
            path = "/frequency-for-word/{word}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> calculateFrequencyForWord(
            @RequestBody final String inputText,
            @PathVariable("word") final String word
    ) {
        final Integer frequencyForWord = wordFrequencyAnalyzer.calculateFrequencyForWord(inputText, word);
        historyLogger.logEvent("/frequency-for-word/{word}", Map.of("inputText", inputText, "word", word), frequencyForWord);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(frequencyForWord);
    }

    @PostMapping(
            path = "/most-frequent-n-words/{nWords}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<WordFrequency>> calculateMostFrequentNWords(
            @RequestBody final String inputText,
            @PathVariable("nWords") final int nWords
    ) {
        final List<WordFrequency> mostFrequentNWords = wordFrequencyAnalyzer.calculateMostFrequentNWords(inputText, nWords);
        historyLogger.logEvent("/most-frequent-n-words/{nWords}", Map.of("inputText", inputText, "nWords", nWords), mostFrequentNWords);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mostFrequentNWords);
    }
}
