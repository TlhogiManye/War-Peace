package com.example.war_peace.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.war_peace.R;
import com.example.war_peace.controllers.MainController;
import com.example.war_peace.utils.FileUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main activity for the application.
 */
public class MainActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private MainController controller;
    private TextView resultView;
    private Button analyzeButton;
    private Button urlAnalyzeButton;
    private EditText urlInput;
    private ProgressBar progressBar;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController();
        resultView = findViewById(R.id.resultView);
        analyzeButton = findViewById(R.id.analyzeButton);
        urlAnalyzeButton = findViewById(R.id.urlAnalyzeButton);
        urlInput = findViewById(R.id.urlInput);
        progressBar = findViewById(R.id.progressBar);
        executorService = Executors.newSingleThreadExecutor();

        analyzeButton.setOnClickListener(v -> openFilePicker());

        urlAnalyzeButton.setOnClickListener(v -> {
            String url = urlInput.getText().toString();
            if (!url.isEmpty()) {
                analyzeUrl(url);
            } else {
                urlInput.setError("Please enter a valid URL");
            }
        });
    }

    /**
     * Opens the file picker to select a file.
     */
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            String filePath = FileUtils.getPath(this, uri);
            analyzeFile(filePath);
        }
    }

    /**
     * Analyzes the selected file and displays the results.
     *
     * @param filePath the path to the file to analyze
     */
    private void analyzeFile(String filePath) {
        showLoader(true);
        executorService.execute(() -> {
            try {
                controller.analyzeFile(filePath);
                runOnUiThread(this::displayResults);
            } catch (IOException e) {
                runOnUiThread(() -> resultView.setText("Error reading file"));
            } finally {
                runOnUiThread(() -> showLoader(false));
            }
        });
    }

    /**
     * Analyzes the text from the provided URL.
     *
     * @param urlString the URL to fetch and analyze
     */
    private void analyzeUrl(String urlString) {
        showLoader(true);
        executorService.execute(() -> {
            String result = fetchTextFromUrl(urlString);
            if (result != null) {
                controller.analyzeText(result);
                runOnUiThread(this::displayResults);
            } else {
                runOnUiThread(() -> resultView.setText("Error reading URL"));
            }
            runOnUiThread(() -> showLoader(false));
        });
    }

    /**
     * Fetches text content from a URL.
     *
     * @param urlString the URL to fetch content from
     * @return the content as a string
     */
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

    /**
     * Displays the analysis results.
     */
    private void displayResults() {
        String mostFrequentWord = controller.getMostFrequentWord();
        String mostFrequent7CharWord = controller.getMostFrequent7CharWord();
        int highestScore = controller.getWordScore(mostFrequentWord);

        String result = "Most frequent word: " + mostFrequentWord + "\n" +
                "Most frequent 7-character word: " + mostFrequent7CharWord + "\n" +
                "Highest scoring word: " + mostFrequentWord + " with a score of " + highestScore;

        resultView.setText(result);
    }

    /**
     * Shows or hides the loader.
     *
     * @param show whether to show the loader or not
     */
    private void showLoader(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
