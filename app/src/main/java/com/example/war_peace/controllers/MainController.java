package com.example.war_peace.controllers;

import android.content.Intent;
import android.net.Uri;

import com.example.war_peace.R;
import com.example.war_peace.view.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller for managing the book analysis.
 */
public class MainController {
    private static final int PICK_FILE_REQUEST = 1;
    private static final String FILE_TYPE_TXT = "text/plain";
    private BookAnalyzer bookAnalyzer;
    private MainActivity activity;
    private ExecutorService executorService;
    public long startTime;

    public MainController(MainActivity activity) {
        this.bookAnalyzer = new BookAnalyzer();
        this.activity = activity;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(FILE_TYPE_TXT); // Only accept .txt files
        activity.startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    public void analyzeFile(String filePath) {
        activity.showLoader(true);
        activity.disableButtons(true);
        startTime = System.currentTimeMillis();
        executorService.execute(() -> {
            try {
                bookAnalyzer.analyzeFile(filePath);
                activity.runOnUiThread(() -> {
                    displayResults();
                    updatePerformance();
                    activity.showLoader(false);
                    activity.disableButtons(false);
                });
            } catch (IOException e) {
                activity.runOnUiThread(() -> {
                    activity.displayResults("Error reading file");
                    activity.showLoader(false);
                    activity.disableButtons(false);
                });
            }
        });
    }

    public void analyzeUrl(String urlString) {
        activity.showLoader(true);
        activity.disableButtons(true);
        startTime = System.currentTimeMillis();
        executorService.execute(() -> {
            String result = fetchTextFromUrl(urlString);
            if (result != null) {
                bookAnalyzer.analyzeText(result);
                activity.runOnUiThread(() -> {
                    displayResults();
                    updatePerformance();
                    activity.showLoader(false);
                    activity.disableButtons(false);
                });
            } else {
                activity.runOnUiThread(() -> {
                    activity.displayResults("Error reading URL, please try again and ensure your network is okay.");
                    activity.showLoader(false);
                    activity.disableButtons(false);
                });
            }
        });
    }

    private String fetchTextFromUrl(String urlString) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }
        return result.toString();
    }

    public void displayResults() {
        String mostFrequentWord = bookAnalyzer.getMostFrequentWord();
        String mostFrequent7CharWord = bookAnalyzer.getMostFrequent7CharWord();
        int highestScore = bookAnalyzer.calculateWordScore(mostFrequentWord);
        int mostFrequentWordCount = bookAnalyzer.getWordCount(mostFrequentWord);

        String result = "Most frequent word: " + mostFrequentWord + " occurred " + mostFrequentWordCount + " times\n" +
                "Most frequent 7-character word: " + mostFrequent7CharWord + "\n" +
                "Highest scoring word: " + mostFrequentWord + " with a score of " + highestScore;

        activity.displayResults(result);
    }

    public void updatePerformance() {
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000; // Duration in seconds
        String performance = "Time taken: " + duration + " seconds";
        activity.updatePerformance(performance);
    }

    public String getPerformanceText() {
        return activity.getString(R.string.performance_text);
    }
}
