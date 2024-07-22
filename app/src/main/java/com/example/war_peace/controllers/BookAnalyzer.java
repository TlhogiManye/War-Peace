package com.example.war_peace.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Analyzes a book to determine word frequencies and scores.
 */
public class BookAnalyzer {
    private Map<String, Integer> wordFrequency;

    public BookAnalyzer() {
        wordFrequency = new HashMap<>();
    }

    /**
     * Analyzes the book from the given file path.
     *
     * @param filePath the path to the file to analyze
     * @throws IOException if an I/O error occurs
     */
    public void analyzeFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                analyzeText(line);
            }
        }
    }

    /**
     * Analyzes the given text for word frequencies.
     *
     * @param text the text to analyze
     */
    public void analyzeText(String text) {
        String[] words = text.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        for (String word : words) {
            // Skip empty strings
            if (!word.isEmpty()) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }
    }

    /**
     * Gets the most frequent word in the book.
     *
     * @return the most frequent word
     */
    public String getMostFrequentWord() {
        return wordFrequency.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("No words found");
    }

    /**
     * Gets the most frequent 7-character word in the book.
     *
     * @return the most frequent 7-character word
     */
    public String getMostFrequent7CharWord() {
        return wordFrequency.entrySet().stream()
                .filter(entry -> entry.getKey().length() == 7)
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No 7-character words found");
    }

    /**
     * Calculates the score of a given word based on letter frequencies.
     *
     * @param word the word to score
     * @return the score of the word
     */
    public int calculateWordScore(String word) {
        int score = 0;
        for (char c : word.toCharArray()) {
            score += getLetterScore(c);
        }
        return score;
    }

    /**
     * Gets the score of a letter.
     *
     * @param letter the letter to score
     * @return the score of the letter
     */
    private int getLetterScore(char letter) {
        switch (letter) {
            case 'a': case 'e': case 'i': case 'l': case 'n': case 'o': case 'r': case 's': case 't': return 1;
            case 'b': case 'c': case 'm': case 'p': return 3;
            case 'd': case 'g': case 'h': case 'k': case 'q': case 'v': case 'w': case 'x': case 'y': return 4;
            case 'j': case 'z': return 8;
            default: return 0; // Unnecessary cases can be omitted or handled differently
        }
    }

    /**
     * Gets the count of a specific word.
     *
     * @param word the word to count
     * @return the count of the word
     */
    public int getWordCount(String word) {
        return wordFrequency.getOrDefault(word, 0);
    }
}
