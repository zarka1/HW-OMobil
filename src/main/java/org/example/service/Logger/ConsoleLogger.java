package org.example.service.Logger;

public class ConsoleLogger implements Logger {
    @Override
    public void logError(String message, String line) {
    }

    @Override
    public void logInfo(String message) {
        System.out.println(message);
    }
}
