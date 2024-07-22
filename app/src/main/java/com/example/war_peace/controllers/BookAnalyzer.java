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
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
            for (String word : words) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }
        reader.close();
    }

    /**
     * Analyzes the given text for word frequencies.
     *
     * @param text the text to analyze
     */
    public void analyzeText(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] words = line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
            for (String word : words) {
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
        return wordFrequency.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
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
                .orElse("");
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
            case 'e': return 1;
            case 'a': return 2;
            case 'r': return 3;
            case 'i': return 4;
            case 'o': return 5;
            case 't': return 6;
            case 'n': return 7;
            case 's': return 8;
            case 'l': return 9;
            case 'c': return 10;
            default: return 11;
        }
    }
}
