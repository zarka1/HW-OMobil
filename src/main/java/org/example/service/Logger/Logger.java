package org.example.service;

public interface Logger {

    void logError(String message, String line);

    void logInfo(String message);
}
