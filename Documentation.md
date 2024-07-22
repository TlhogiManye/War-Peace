# War & Peace Book Analyzer Documentation

## Overview

The War & Peace Book Analyzer is a Java-based application designed to analyze text data from "War and Peace" or other text sources. It calculates word frequencies, identifies the most frequent words and 7-character words, and assigns scores to words based on predefined letter values. The application also tracks and displays performance metrics related to the analysis process.

## Features

1. **File Analysis**:
    - **Input**: Text files containing the book content.
    - **Output**: Word frequencies, most frequent words, and word scores.

2. **URL Analysis**:
    - **Input**: URLs pointing to text content.
    - **Output**: Similar analysis results as file analysis.

3. **Word Scoring**:
    - Calculates the score of a word based on letter frequencies:
        - `A, E, I, L, N, O, R, S, T, U`: 1 point
        - `B, C, G, M, P`: 3 points
        - `D, F, H, V, W, Y`: 4 points
        - `K`: 5 points
        - `J, X`: 8 points
        - `Q, Z`: 10 points

4. **Performance Metrics**:
    - Measures the time taken for the analysis.
    - Displays the processing time to provide insights into the efficiency of the application.

## Architecture

### Classes and Responsibilities

1. **BookAnalyzer**:
    - **Purpose**: Manages the text analysis operations.
    - **Key Methods**:
        - `analyzeFile(String filePath)`: Reads and analyzes text from a file. Updates word frequency map.
        - `analyzeText(String text)`: Analyzes text directly. Useful for analyzing text fetched from a URL.
        - `getMostFrequentWord()`: Returns the most frequently occurring word.
        - `getMostFrequent7CharWord()`: Returns the most frequent 7-character word.
        - `calculateWordScore(String word)`: Calculates the score of a word based on predefined letter values.
        - `getWordCount(String word)`: Retrieves the count of a specific word.

2. **MainController**:
    - **Purpose**: Handles user interactions and orchestrates the analysis workflow.
    - **Key Methods**:
        - `openFilePicker()`: Launches a file picker dialog for users to select files.
        - `analyzeFile(String filePath)`: Starts the file analysis process and manages the UI state during analysis.
        - `analyzeUrl(String urlString)`: Fetches text from a URL and initiates analysis.
        - `displayResults()`: Formats and displays the results of the analysis.
        - `updatePerformance()`: Calculates and displays the performance metrics.
        - `getPerformanceText()`: Retrieves performance-related text from resources.

### Design Principles

1. **Single Responsibility Principle (SRP)**:
    - **BookAnalyzer**: Focuses on analyzing text and scoring words. Handles all text processing responsibilities.
    - **MainController**: Manages user interactions, controls the workflow of the application, and updates the UI based on analysis results.

2. **Open/Closed Principle (OCP)**:
    - The system is designed to be open for extension but closed for modification. For example, you can extend the `BookAnalyzer` to add new features without changing the existing codebase.

3. **Liskov Substitution Principle (LSP)**:
    - Derived classes or extended versions of `BookAnalyzer` should be able to replace the base class without affecting the correctness of the program.

4. **Interface Segregation Principle (ISP)**:
    - Although the application does not use interfaces directly, methods are kept focused and specific to their functionality to avoid creating large, unwieldy classes.

5. **Dependency Inversion Principle (DIP)**:
    - The `MainController` depends on abstractions (e.g., the `BookAnalyzer` class) rather than concrete implementations. This promotes flexibility and easier testing.

## Setup and Usage

### Running the Application

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/war-peace-analyzer.git
