/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationtoapp;

/**
 *
 * @author Student
 */
public class SentMessages {
    private static final SecureRandom RANDOM = new SecureRandom();

    private final String messageId;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private final int messageNumber;

    public SentMessages(String recipient, String messageText, int messageNumber) {
        this.messageId = generateMessageId();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageHash = generateMessageHash();
    }

    private String generateMessageId() {
        return String.valueOf(
            1_000_000_000L + RANDOM.nextLong(9_000_000_000L)
        );
    }

    private String generateMessageHash() {
        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll("[^\\p{L}\\p{N}]", "");
        String lastWord = words[words.length - 1]
                .replaceAll("[^\\p{L}\\p{N}]", "");

        return (messageId.substring(0, 2) + ":"
                + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String getMessageId() {
        return messageId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    @Override
    public String toString() {
        return "Message ID: " + messageId
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }
}

