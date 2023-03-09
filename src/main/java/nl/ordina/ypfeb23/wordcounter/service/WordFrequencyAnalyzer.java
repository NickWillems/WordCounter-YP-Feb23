package nl.ordina.ypfeb23.wordcounter.service;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class WordFrequencyAnalyzer {

    /**
     * Find the word with the highest frequency in full text string
     * Could be multiple words
     *
     * @param text Full text
     * @return Word(s) with the highest frequency
     */
    public int calculateHighestFrequency(final String text) {
        final HashMap<String, Integer> parsedText = parseText(text);

        int highestFrequency = 0;
        for (final Map.Entry<String, Integer> wordFrequencyEntry : parsedText.entrySet()) {
            if (highestFrequency < 1 || wordFrequencyEntry.getValue() > highestFrequency) {
                highestFrequency = wordFrequencyEntry.getValue();
            }
        }

        return highestFrequency;
    }

    /**
     * Find the frequency for a specific word in full text string
     *
     * @param text Full text
     * @param word Word to calculate frequency for
     * @return Frequency of the word
     */
    public int calculateFrequencyForWord(final String text, final String word) {
        final HashMap<String, Integer> parsedText = parseText(text);

        // Return 0 if the word is not in parsedText
        return parsedText.getOrDefault(word.toLowerCase(), 0);
    }

    /**
     * Create list of most frequent n number of words in input text
     * If several have same frequency, return them in ascending order
     *
     * @param text Full text
     * @param n Number of WordFrequency's to return
     * @return N most frequent words from input text
     */
    public List<WordFrequency> calculateMostFrequentNWords(final String text, final int n) {
        final HashMap<String, Integer> parsedText = parseText(text);

        List<WordFrequency> mostFrequentNWords = new ArrayList<>();
        for (Map.Entry<String, Integer> wordFrequencyEntry : parsedText.entrySet()) {
            Integer insertAtIndex = null;

            // If mostFrequentNWords list is empty, always add wordFrequency to the list
            if (mostFrequentNWords.isEmpty()) {
                mostFrequentNWords.add(new WordFrequency(wordFrequencyEntry.getKey(), wordFrequencyEntry.getValue()));

                // Skip until next iteration because wordFrequency is already added mostFrequentNWords list
                continue;
            }

            // Iterate backwards through mostFrequentNWords to check if wordFrequency should be inserted before any value
            for (int i = mostFrequentNWords.size(); i-- > 0;) {
                if (wordHasHigherOrder(
                        mostFrequentNWords.get(i),
                        new WordFrequency(wordFrequencyEntry.getKey(), wordFrequencyEntry.getValue()))
                ) {
                    insertAtIndex = i;
                }
            }

            if (insertAtIndex != null) {
                // Put wordFrequency at the chosen index of mostFrequentNWords list
                mostFrequentNWords.add(
                        insertAtIndex,
                        new WordFrequency(wordFrequencyEntry.getKey(), wordFrequencyEntry.getValue())
                );
            } else {
                // If insertAtIndex is null, put wordFrequency at the end of mostFrequentNWords list
                mostFrequentNWords.add(new WordFrequency(wordFrequencyEntry.getKey(), wordFrequencyEntry.getValue()));
            }

            // When item was added at any position in mostFrequentNWords list, lowest ranking moved down
            // When list size exceeds n, remove lowest ranking item
            // At this point list can have a maximum size of n+1, so no loop needed
            if (mostFrequentNWords.size() > n) {
                // (0, n) or (0, n+1) ?
                mostFrequentNWords = mostFrequentNWords.subList(0, n);
            }
        }

        return mostFrequentNWords;
    }

    /**
     * Takes a full text string and turns it into a HashMap with key:word and value:frequency
     *
     * @param text Full text
     * @return Words HashMap with word key and frequency value
     */
    private HashMap<String, Integer> parseText(final String text) {
        // Use regex "\\P{IsAlphabetic}+" to split string by any non-alphabetic character and make all lowercase
        final List<String> separatedWords = Arrays.stream(text.split("\\P{IsAlphabetic}+")).map(String::toLowerCase).toList();

        // Build Word-Frequency HashMap
        final HashMap<String, Integer> frequencyByWord = new HashMap<>();
        for (final String word : separatedWords) {
            if (frequencyByWord.containsKey(word)) {
                final int currentWordCount = frequencyByWord.get(word);
                frequencyByWord.put(word, currentWordCount + 1);
            } else {
                frequencyByWord.put(word, 1);
            }
        }

        return frequencyByWord;
    }

    /**
     * Check if the new word should have a higher order than then existing word
     * Based on highest frequency first and alphabetical order (ascending) if frequency is the same
     *
     * @param existingWordFrequency Existing word
     * @param newWordFrequency New word to check against the existing
     * @return boolean, the new word should have a higher order than the existing one
     */
    private boolean wordHasHigherOrder(final WordFrequency existingWordFrequency, final WordFrequency newWordFrequency) {
        // Check if existingWordFrequency has a higher frequency
        if (existingWordFrequency.frequency() > newWordFrequency.frequency()) {
            // newWordFrequency should NOT have a higher order
            return false;
        } else if (existingWordFrequency.frequency() < newWordFrequency.frequency()) {
            // newWordFrequency should have a higher order
            return true;
        } else {
            // newWordFrequency has the same frequency, check alphabetically
            // if newWordFrequency comes first alphabetically it should a have higher order
            return existingWordFrequency.word().compareTo(newWordFrequency.word()) > 0;
        }
    }
}
