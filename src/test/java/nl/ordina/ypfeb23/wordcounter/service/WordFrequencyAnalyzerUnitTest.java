package nl.ordina.ypfeb23.wordcounter.service;

import nl.ordina.ypfeb23.wordcounter.TestUtil;
import org.junit.jupiter.api.*;

import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WordFrequencyAnalyzerUnitTest {
    // Arrange
    private final WordFrequencyAnalyzer testSubject = new WordFrequencyAnalyzer();

    @Test
    void shouldCalculateHighestFrequency() {
        // Act
        final int result = testSubject.calculateHighestFrequency(TestUtil.DEFAULT_TEST_STRING);

        // Assert
        Assertions.assertEquals(4, result);
    }

    @Test
    void shouldCalculateFrequencyForWord() {
        // Act
        final int result1 = testSubject.calculateFrequencyForWord(TestUtil.DEFAULT_TEST_STRING, "Word");
        final int result2 = testSubject.calculateFrequencyForWord(TestUtil.DEFAULT_TEST_STRING, "word");
        final int result3 = testSubject.calculateFrequencyForWord(TestUtil.DEFAULT_TEST_STRING, "nonExistentWord");

        // Assert
        Assertions.assertEquals(4, result1);
        Assertions.assertEquals(4, result2);
        Assertions.assertEquals(0, result3);
    }

    @Test
    public void shouldNotCalculateFrequencyForWord_wordNotAlphaNumerical() {
        // Act
        final int result = testSubject.calculateFrequencyForWord(TestUtil.DEFAULT_TEST_STRING, "4");

        // Assert
        Assertions.assertEquals(0, result);
    }

    @Test
    void shouldCalculateMostFrequentNWords() {
        // Act
        final List<WordFrequency> result = testSubject.calculateMostFrequentNWords(TestUtil.DEFAULT_TEST_STRING, 3);

        // Assert
        Assertions.assertEquals(3, result.size());
        Assertions.assertTrue(result.stream().anyMatch(o -> o.word().equals("word") && o.frequency() == 4));
        Assertions.assertTrue(result.stream().anyMatch(o -> o.word().equals("a") && o.frequency() == 1));
        Assertions.assertTrue(result.stream().anyMatch(o -> o.word().equals("frequency") && o.frequency() == 1));
        Assertions.assertFalse(result.stream().anyMatch(o -> o.word().equals("has") && o.frequency() == 1));
        Assertions.assertFalse(result.stream().anyMatch(o -> o.word().equals("of") && o.frequency() == 1));
    }

    @Test
    public void shouldNotCalculateMostFrequentNWords_nHigherThanAmountOfWordsInString() {
        // Arrange
        final int nWords = 99;

        // Act
        final List<WordFrequency> result = testSubject.calculateMostFrequentNWords(TestUtil.DEFAULT_TEST_STRING, nWords);

        // Assert
        Assertions.assertTrue(result.size() < nWords);
    }
}