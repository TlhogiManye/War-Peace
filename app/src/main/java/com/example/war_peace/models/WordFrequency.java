/**
 * Class that represents the word frequency of a particular word
 *
 * @author  MD Manye
 */

package com.example.war_peace.models;

public class WordFrequency {
    private String word;
    private int frequency;

    /**
     * Represents a word and its frequency.
     */
    public WordFrequency(String word, int frequency){
        this.word = word;
        this.frequency = frequency;
    }
    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }

}
