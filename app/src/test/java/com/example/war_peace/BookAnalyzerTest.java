package com.example.war_peace;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

import com.example.war_peace.controllers.BookAnalyzer;


public class BookAnalyzerTest {

    private BookAnalyzer bookAnalyzer;

    @Before
    public void setUp() {
        bookAnalyzer = new BookAnalyzer();
    }

    @Test
    public void testAnalyzeFile() throws IOException {
        // Create a temporary file with sample content
        String filePath = "testfile.txt";
        try (java.io.PrintWriter out = new java.io.PrintWriter(filePath)) {
            out.println("hello world");
            out.println("hello again");
        }

        // Analyze the file
        bookAnalyzer.analyzeFile(filePath);

        // Verify the word counts
        assertEquals(2, bookAnalyzer.getWordCount("hello"));
        assertEquals(1, bookAnalyzer.getWordCount("world"));
        assertEquals(0, bookAnalyzer.getWordCount("missing"));
    }

    @Test
    public void testAnalyzeText() {
        String text = "hello world\nhello again";

        // Analyze the text
        bookAnalyzer.analyzeText(text);

        // Verify the word counts
        assertEquals(2, bookAnalyzer.getWordCount("hello"));
        assertEquals(1, bookAnalyzer.getWordCount("world"));
        assertEquals(0, bookAnalyzer.getWordCount("missing"));
    }

    @Test
    public void testGetMostFrequentWord() {
        bookAnalyzer.analyzeText("hello world hello");

        // Verify the most frequent word
        assertEquals("hello", bookAnalyzer.getMostFrequentWord());
    }

    @Test
    public void testGetMostFrequent7CharWord() {
        bookAnalyzer.analyzeText("someone somewhere");

        // Verify the most frequent 7-character word
        assertEquals("someone", bookAnalyzer.getMostFrequent7CharWord());
    }

    @Test
    public void testCalculateWordScore() {
        // Test words with known scores
        assertEquals(5, bookAnalyzer.calculateWordScore("hello")); // h(4) + e(1) + l(1) + l(1) + o(1) = 5
        assertEquals(11, bookAnalyzer.calculateWordScore("jumpy")); // j(8) + u(1) + m(3) + p(3) + y(4) = 11
    }

    @Test
    public void testGetWordCount() {
        bookAnalyzer.analyzeText("apple apple banana");

        // Verify the count of specific words
        assertEquals(2, bookAnalyzer.getWordCount("apple"));
        assertEquals(1, bookAnalyzer.getWordCount("banana"));
        assertEquals(0, bookAnalyzer.getWordCount("orange"));
    }
}
