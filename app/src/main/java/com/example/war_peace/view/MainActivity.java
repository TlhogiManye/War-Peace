package com.example.war_peace.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private MainController controller;
    private TextView resultView;
    private TextView performanceView;
    private Button analyzeButton;
    private Button urlAnalyzeButton;
    private EditText urlInput;
    private ProgressBar progressBar;
    private ExecutorService executorService;
    private long startTime;

    private Button performanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(this);
        resultView = findViewById(R.id.resultView);
        performanceView = findViewById(R.id.performanceView);
        analyzeButton = findViewById(R.id.analyzeButton);
        urlAnalyzeButton = findViewById(R.id.urlAnalyzeButton);
        urlInput = findViewById(R.id.urlInput);
        progressBar = findViewById(R.id.progressBar);
        executorService = Executors.newSingleThreadExecutor();

        analyzeButton.setOnClickListener(v -> controller.openFilePicker());

        urlAnalyzeButton.setOnClickListener(v -> {
            String url = urlInput.getText().toString();
            if (!url.isEmpty()) {
                controller.analyzeUrl(url);
            } else {
                urlInput.setError("Please enter a valid URL");
            }
        });

        performanceButton = findViewById(R.id.performanceButton);
        performanceButton.setOnClickListener(v -> showPerformanceDialog());
    }

    private void showPerformanceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_performance, null);
        builder.setView(dialogView);

        TextView performanceContent = dialogView.findViewById(R.id.performanceContent);
        performanceContent.setText(controller.getPerformanceText());

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            String filePath = FileUtils.getPath(this, uri);
            controller.analyzeFile(filePath);
        }
    }

    public void displayResults(String result) {
        resultView.setText(result);
    }

    public void updatePerformance(String performance) {
        performanceView.setText(performance);
    }

    public void showLoader(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void disableButtons(boolean disable) {
        analyzeButton.setEnabled(!disable);
        urlAnalyzeButton.setEnabled(!disable);
        if (disable) {
            Snackbar.make(findViewById(android.R.id.content), "Processing, please wait...", Snackbar.LENGTH_LONG).show();
        }
    }
}
