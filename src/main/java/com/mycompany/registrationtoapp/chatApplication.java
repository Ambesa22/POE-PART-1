/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationtoapp;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Student
 */


public class chatApplication {

    private final Scanner input;
    private final List<SentMessages> sentMessages = new ArrayList<>();
    private final List<SentMessages> storedMessages = new ArrayList<>();

    private int totalMessagesSent = 0;
    private int messageNumber = 0;

    public chatApplication(Scanner input) {
        this.input = input;
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== QUICKCHAT MENU =====");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    sendMessages();
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    System.out.println("Thank you for using QuickChat.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Choose 1, 2 or 3.");
            }
        }
    }

    private void sendMessages() {
        int numberOfMessages;

        while (true) {
            System.out.print("How many messages do you want to enter? ");

            try {
                numberOfMessages = Integer.parseInt(input.nextLine().trim());

                if (numberOfMessages > 0) {
                    break;
                }

                System.out.println("Enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }

        for (int i = 0; i < numberOfMessages; i++) {
            System.out.println("\nMessage " + (i + 1));

            String recipient;

            while (true) {
                System.out.print("Enter recipient number (+27XXXXXXXXX): ");
                recipient = input.nextLine().trim();

                if (isValidRecipient(recipient)) {
                    System.out.println("Cell phone number successfully captured.");
                    break;
                }

                System.out.println(
                    "Invalid number. Use +27 followed by nine digits."
                );
            }

            String text;

            while (true) {
                System.out.print("Enter message (maximum 250 characters): ");
                text = input.nextLine();

                if (isValidMessage(text)) {
                    break;
                }

                System.out.println(
                    "Message must contain text and be no longer than 250 characters."
                );
            }

            SentMessages message = new SentMessages(recipient, text, messageNumber);

            System.out.println("\nMessage ID: " + message.getMessageId());
            System.out.println("Message Hash: " + message.getMessageHash());

            System.out.println("\n1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");
            System.out.print("Choose an action: ");

            String action = input.nextLine().trim();

            switch (action) {
                case "1":
                    sentMessages.add(message);
                    totalMessagesSent++;
                    messageNumber++;

                    System.out.println("Message successfully sent.");
                    System.out.println(message);
                    break;

                case "2":
                    System.out.println("Press 0 to delete the message.");
                    String deleteChoice = input.nextLine().trim();

                    if ("0".equals(deleteChoice)) {
                        System.out.println("Message disregarded.");
                    } else {
                        System.out.println("Message discarded.");
                    }
                    break;

                case "3":
                    storedMessages.add(message);
                    messageNumber++;

                    try {
                        storeMessages();
                        System.out.println("Message successfully stored.");
                    } catch (IOException e) {
                        storedMessages.remove(message);
                        messageNumber--;
                        System.out.println("Could not store message: "
                                + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Message discarded.");
            }
        }

        System.out.println("\nTotal messages sent: " + totalMessagesSent);
    }

    public static boolean isValidMessage(String text) {
        return text != null
                && !text.trim().isEmpty()
                && text.length() <= 250;
    }

    public static boolean isValidRecipient(String recipient) {
        return recipient != null
                && recipient.matches("^\\+27[0-9]{9}$");
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public String printMessages() {
        StringBuilder result = new StringBuilder();

        for (SentMessages message : sentMessages) {
            result.append(message).append("\n\n");
        }

        return result.toString();
    }

    private void storeMessages() throws IOException {
        StringBuilder json = new StringBuilder("[\n");

        for (int i = 0; i < storedMessages.size(); i++) {
            SentMessages message = storedMessages.get(i);

            json.append("  {\n")
                .append("    \"messageId\": \"")
                .append(escapeJson(message.getMessageId())).append("\",\n")
                .append("    \"messageHash\": \"")
                .append(escapeJson(message.getMessageHash())).append("\",\n")
                .append("    \"recipient\": \"")
                .append(escapeJson(message.getRecipient())).append("\",\n")
                .append("    \"message\": \"")
                .append(escapeJson(message.getMessageText())).append("\"\n")
                .append("  }");

            if (i < storedMessages.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append("]\n");

        Files.writeString(
            Path.of("messages.json"),
            json.toString(),
            StandardCharsets.UTF_8
        );
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}


