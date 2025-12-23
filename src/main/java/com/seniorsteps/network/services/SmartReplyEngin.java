package com.seniorsteps.network.services;

import java.util.Date;

public class SmartReplyEngin {
    public String reply(String message) {
        if (message == null) return "Message cannot be null";

        String cleanMessage = message.trim().toLowerCase();

        return switch (cleanMessage) {
            case "hi", "hello", "welcome" -> "Welcome to the server!";
            case "how are you", "how are you?", "how r u?", "how u?" -> "I'm fine. How about you?";
            case "bye", "bye!", "good bye", "exit", "close" -> "exit";
            case "time", "time?", "date" -> new Date().toString();
            default -> "Sorry, I didn't catch that.";
        };
    }
}