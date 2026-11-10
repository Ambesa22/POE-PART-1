/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.registrationtoapp;

import java.security.SecureRandom;
import java.util.Locale;
/**
 * 
 * .
 *
 * @author Student
 */
public class SentMessages {

 
    private static final SecureRandom RANDOM = new SecureRandom();

    // Message details
    private final String messageId;
    private final String sender;
    private final String receiver;
    private final String messageText;
    private final String messageHash;
    private final int messageNumber;

    // Constructor for Part 2 compatibility.
    // Uses QuickChat as the sender if no sender name is provided.
    public SentMessages(String receiver, String messageText,
                        int messageNumber) {

        this("QuickChat", receiver, messageText, messageNumber);
    }

    // Constructor including the sender's name.
    public SentMessages(String sender, String receiver,
                        String messageText, int messageNumber) {

        if (sender == null || sender.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Sender cannot be empty.");
        }

        if (receiver == null || receiver.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Recipient cannot be empty.");
        }

        if (messageText == null || messageText.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Message cannot be empty.");
        }

        if (messageText.length() > 250) {
            throw new IllegalArgumentException(
                    "Message cannot exceed 250 characters.");
        }

        this.sender = sender;
        this.receiver = receiver;
        this.messageText = messageText;
        this.messageNumber = messageNumber;

        this.messageId = generateMessageId();
        this.messageHash = generateMessageHash();
    }

    // PART 2: Generate a random 10-digit message ID.
    private String generateMessageId() {

        long randomNumber =
                1_000_000_000L + RANDOM.nextLong(9_000_000_000L);

        String id = String.valueOf(randomNumber);

        return id.substring(0, 10);
    }

    // PART 2: Generate the message hash.
    // The hash uses:
    // First two digits of the message ID,
    // message number,
    // first word and last word of the message.
    private String generateMessageHash() {

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll(
                "[^\\p{L}\\p{N}]", "");

        String lastWord = words[words.length - 1].replaceAll(
                "[^\\p{L}\\p{N}]", "");

        String hash = messageId.substring(0, 2)
                + ":" + messageNumber
                + ":" + firstWord
                + lastWord;

        return hash.toUpperCase(Locale.ROOT);
    }

    // PART 2: Check whether the message is valid.
    public static boolean checkMessage(String messageText) {

        return messageText != null
                && !messageText.trim().isEmpty()
                && messageText.length() <= 250;
    }

    // PART 2: Return the message ID.
    public String getMessageId() {
        return messageId;
    }

    // PART 2: Return the message hash.
    public String getMessageHash() {
        return messageHash;
    }

    // PART 2 AND PART 3: Return the sender.
    public String getSender() {
        return sender;
    }

    // PART 2 AND PART 3: Return the recipient.
    public String getReceiver() {
        return receiver;
    }

    // PART 2 AND PART 3: Return the message text.
    public String getMessageText() {
        return messageText;
    }

    // PART 2 AND PART 3: Return the message number.
    public int getMessageNumber() {
        return messageNumber;
    }

    // PART 2: Display the message details.
    @Override
    public String toString() {

        return "Message ID: " + messageId
                + "\nMessage Hash: " + messageHash
                + "\nSender: " + sender
                + "\nRecipient: " + receiver
                + "\nMessage: " + messageText
                + "\nMessage Number: " + messageNumber;
    }
}
