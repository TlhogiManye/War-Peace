package com.example.war_peace.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Calculates the score of a word based on Scrabble letter values.
 */
public class ScrabbleScorer {
    private static final Map<Character, Integer> scoreTable = new HashMap<>();

    static {
        scoreTable.put('a', 1);
        scoreTable.put('b', 3);
        scoreTable.put('c', 3);

    }

    /**
     * Calculates the Scrabble score of a word.
     *
     * @param word the word to score
     * @return the score of the word
     */
    public int calculateScore(String word) {
        int score = 0;
        for (char c : word.toCharArray()) {
            score += scoreTable.getOrDefault(c, 0);
        }
        return score;
    }
}
