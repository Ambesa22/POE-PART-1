/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.registrationtoapp;

import java.security.SecureRandom;

/**
 *
 * @author Student
 */
public class SentMessages {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final String messageId;
    private final String receiver;
    private final String messageText;
    private final String messageHash;
    private final int messageNumber;

    // Constructor
    public SentMessages(String recipient, String messageText,
                        int messageNumber) {

        if (recipient == null || messageText == null
                || messageText.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Recipient and message cannot be empty."
            );
        }

        this.messageId = generateMessageId();
        this.receiver = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageHash = generateMessageHash();
    }

    // Generate a random ten-digit message ID
    private String generateMessageId() {
        return String.valueOf(
            1_000_000_000L + RANDOM.nextLong(9_000_000_000L)
        );
    }

    // Generate the message hash
    private String generateMessageHash() {

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0]
                .replaceAll("[^\\p{L}\\p{N}]", "");

        String lastWord = words[words.length - 1]
                .replaceAll("[^\\p{L}\\p{N}]", "");

        return (messageId.substring(0, 2) + ":"
                + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    // Return the message ID
    public String getMessageId() {
        return messageId;
    }

    // Return the receiver's phone number
    public String getReceiver() {
        return receiver;
    }

    // Return the message text
    public String getMessageText() {
        return messageText;
    }

    // Return the message hash
    public String getMessageHash() {
        return messageHash;
    }

    // Return the message number
    public int getMessageNumber() {
        return messageNumber;
    }

    // Display the message details
    @Override
    public String toString() {
        return "Message ID: " + messageId
                + "\nMessage Hash: " + messageHash
                + "\nReceiver: " + receiver
                + "\nMessage: " + messageText;
    }
}
