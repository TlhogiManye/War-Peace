package com.example.war_peace.controllers;

import java.io.*;
import java.util.*;

/**
 * Analyzes a book to determine word frequencies.
 */
public class BookAnalyzer {
    private Map<String, Integer> wordFrequency = new HashMap<>();

    /**
     * Analyzes the given file for word frequencies.
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
     * Returns the word frequency map.
     *
     * @return a map of words to their frequencies
     */
    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }

    /**
     * Returns the most frequent word.
     *
     * @return the most frequent word
     */
    public String getMostFrequentWord() {
        return Collections.max(wordFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /**
     * Returns the most frequent 7-character word.
     *
     * @return the most frequent 7-character word
     */
    public String getMostFrequent7CharWord() {
        return wordFrequency.entrySet().stream()
                .filter(entry -> entry.getKey().length() == 7)
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }
}
