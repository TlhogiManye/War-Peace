package com.example.war_peace.models;

public class WordFrequency {
    private String word;
    private int frequency;

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
