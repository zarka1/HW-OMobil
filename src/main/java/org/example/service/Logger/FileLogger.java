package org.example.service.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class FileLogger implements Logger {
    private String logFile;


    public FileLogger(String filePath) {
        logFile = filePath;
    }

    @Override
    public void logError(String message, String line) {
        try (FileWriter fileWriter = new FileWriter(logFile, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            String timestamp = LocalDateTime.now().toString();
            printWriter.println(timestamp + " - ERROR: " + message);
            printWriter.println("Faulty Line: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logInfo(String message) {
        try (FileWriter fileWriter = new FileWriter(logFile, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            String timestamp = LocalDateTime.now().toString();
            printWriter.println(timestamp + " - INFO: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
