package com.example.war_peace.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.war_peace.R;
import com.example.war_peace.controllers.MainController;
import com.example.war_peace.utils.FileUtils;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private MainController controller;
    private TextView resultView;
    private TextView performanceView;
    private ProgressBar progressBar;
    private Button urlAnalyzeButton;
    private Button analyzeButton;
    private Button performanceButton;
    private EditText urlInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        resultView = findViewById(R.id.resultView);
        performanceView = findViewById(R.id.performanceView);
        progressBar = findViewById(R.id.progressBar);
        urlAnalyzeButton = findViewById(R.id.urlAnalyzeButton);
        analyzeButton = findViewById(R.id.analyzeButton);
        performanceButton = findViewById(R.id.performanceButton);
        urlInput = findViewById(R.id.urlInput);

        controller = new MainController(this);

        analyzeButton.setOnClickListener(v -> controller.openFilePicker());
        urlAnalyzeButton.setOnClickListener(v -> analyzeUrl());
        performanceButton.setOnClickListener(v -> updatePerformance());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            String filePath = FileUtils.getPath(this, uri);
            if (filePath != null) {
                controller.analyzeFile(filePath);
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Unable to get file path", Snackbar.LENGTH_LONG).show();

            }
        }
    }

    public void displayResults(String results) {
        resultView.setText(results);
    }

    public void updatePerformance(String performance) {
        performanceView.setText(performance);
    }

    public void showLoader(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void disableButtons(boolean disable) {
        urlAnalyzeButton.setEnabled(!disable);
        analyzeButton.setEnabled(!disable);
        performanceButton.setEnabled(!disable);
    }

    private void analyzeUrl() {
        String url = urlInput.getText().toString().trim();
        if (!url.isEmpty()) {
            controller.analyzeUrl(url);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please enter a valid URL", Snackbar.LENGTH_LONG).show();
        }
    }

    private void updatePerformance() {
        // Example function to handle performance button click
        String performanceText = controller.getPerformanceText();
        performanceView.setText(performanceText);
    }
}
