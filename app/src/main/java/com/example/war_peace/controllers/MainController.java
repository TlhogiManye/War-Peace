package com.example.war_peace.controllers;

import java.io.IOException;

/**
 * Controller for managing the book analysis.
 */
public class MainController {
    private BookAnalyzer bookAnalyzer;

    public MainController() {
        bookAnalyzer = new BookAnalyzer();
    }

    /**
     * Analyzes the file at the given path.
     *
     * @param filePath the path to the file to analyze
     * @throws IOException if an I/O error occurs
     */
    public void analyzeFile(String filePath) throws IOException {
        bookAnalyzer.analyzeFile(filePath);
    }

    /**
     * Analyzes the given text.
     *
     * @param text the text to analyze
     */
    public void analyzeText(String text) {
        bookAnalyzer.analyzeText(text);
    }

    /**
     * Gets the most frequent word.
     *
     * @return the most frequent word
     */
    public String getMostFrequentWord() {
        return bookAnalyzer.getMostFrequentWord();
    }

    /**
     * Gets the most frequent 7-character word.
     *
     * @return the most frequent 7-character word
     */
    public String getMostFrequent7CharWord() {
        return bookAnalyzer.getMostFrequent7CharWord();
    }

    /**
     * Gets the word score for a given word.
     *
     * @param word the word to score
     * @return the score of the word
     */
    public int getWordScore(String word) {
        return bookAnalyzer.calculateWordScore(word);
    }
}
