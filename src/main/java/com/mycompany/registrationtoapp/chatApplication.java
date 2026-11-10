/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationtoapp;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;



/**
 * 
 *
 * 
 * 
 *
 * @author Student
 */


public class chatApplication {
 private final Scanner input;
    private final ManageMessages manager;

    private int messageNumber = 0;
    private String senderName = "QuickChat";

    // Constructor used by RegistrationToApp.java.
    public chatApplication(Scanner input) {
        this(input, "QuickChat");
    }

    // Constructor allowing the logged-in user's name to be used.
    public chatApplication(Scanner input, String senderName) {

        this.input = input;
        this.senderName = (senderName == null
                || senderName.trim().isEmpty())
                ? "QuickChat" : senderName.trim();

        this.manager = new ManageMessages();

        // Continue numbering from previously stored messages.
        for (SentMessages message : manager.getStoredMessages()) {
            if (message.getMessageNumber() > messageNumber) {
                messageNumber = message.getMessageNumber();
            }
        }
    }

    // PART 1, 2 AND 3: Start the application.
    public void run() {

        boolean running = true;

        System.out.println("\n================================");
        System.out.println("       WELCOME TO QUICKCHAT");
        System.out.println("================================");

        while (running) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Recently Sent Messages");
            System.out.println("3. Stored Messages");
            System.out.println("4. Quit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {

                case "1":
                    sendMessages();
                    break;

                case "2":
                    showSentMessages();
                    break;

                case "3":
                    storedMessagesMenu();
                    break;

                case "4":
                    System.out.println(
                            "Thank you for using QuickChat. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose 1, 2, 3 or 4.");
            }
        }
    }

    // PART 2: Send, store or disregard messages.
    public void sendMessages() {

        boolean sending = true;

        while (sending) {

            System.out.println("\n========== SEND MESSAGE ==========");

            System.out.print("Enter recipient cellphone number: ");
            String recipient = input.nextLine().trim();

            if (!isValidRecipient(recipient)) {

                System.out.println(
                        "Invalid recipient cellphone number.");

                System.out.println(
                        "Use +27 followed by 9 digits, "
                        + "or 0 followed by 9 digits.");

                continue;
            }

            System.out.print("Enter your message: ");
            String messageText = input.nextLine();

            if (!isValidMessage(messageText)) {

                System.out.println(
                        "Invalid message. Messages cannot be empty "
                        + "or exceed 250 characters.");

                continue;
            }

            // Increment for every message created,
            // including messages later disregarded.
            messageNumber++;

            SentMessages message;

            try {

                message = new SentMessages(
                        senderName,
                        recipient,
                        messageText,
                        messageNumber
                );

            } catch (IllegalArgumentException exception) {

                System.out.println(exception.getMessage());
                continue;
            }

            System.out.println("\nMessage created successfully.");
            System.out.println("Message ID: "
                    + message.getMessageId());

            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {

                case "1":

                    manager.addSentMessage(message);

                    System.out.println("\nMessage successfully sent.");
                    System.out.println("Message Hash: "
                            + message.getMessageHash());

                    System.out.println(message.getMessageText());
                    break;

                case "2":

                    manager.addDisregardedMessage(message);

                    System.out.println(
                            "Message disregarded successfully.");

                    // Do not display the hash for a disregarded message.
                    break;

                case "3":

                    if (manager.addStoredMessage(message)) {

                        System.out.println(
                                "Message successfully stored.");

                        System.out.println("Message Hash: "
                                + message.getMessageHash());

                    } else {

                        System.out.println(
                                "Message could not be stored. "
                                + "Please try again.");
                    }
                    break;

                default:

                    manager.addDisregardedMessage(message);

                    System.out.println(
                            "Invalid choice. Message disregarded.");

                    break;
            }

            System.out.print(
                    "\nWould you like to create another message? (Y/N): ");

            String another = input.nextLine().trim();

            if (!another.equalsIgnoreCase("Y")) {
                sending = false;
            }
        }
    }

    // PART 2: Validate the message.
    public static boolean isValidMessage(String messageText) {

        return messageText != null
                && !messageText.trim().isEmpty()
                && messageText.length() <= 250;
    }

    // PART 2: Validate the recipient's South African number.
    public static boolean isValidRecipient(String recipient) {

        return recipient != null
                && (recipient.matches("^\\+27[0-9]{9}$")
                || recipient.matches("^0[0-9]{9}$"));
    }

    // PART 2: Return the total number of successfully sent messages.
    public int returnTotalMessages() {

        return manager.getSentMessages().size();
    }

    // PART 2: Print all successfully sent messages.
    public void printMessages() {

        List<SentMessages> messages = manager.getSentMessages();

        if (messages.isEmpty()) {

            System.out.println("No messages have been sent yet.");
            return;
        }

        System.out.println("\n========== SENT MESSAGES ==========");

        for (SentMessages message : messages) {
            System.out.println(message);
            System.out.println("-----------------------------------");
        }
    }

    // PART 2: Display recently sent messages.
    public void showSentMessages() {

        System.out.println("\n========== RECENTLY SENT MESSAGES ==========");

        printMessages();

        System.out.println("Total messages sent: "
                + returnTotalMessages());
    }

    // PART 3: Stored messages menu.
    public void storedMessagesMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n========== STORED MESSAGES MENU ==========");
            System.out.println("1. Display sender and recipient of stored messages");
            System.out.println("2. Display the longest stored message");
            System.out.println("3. Search for a message using message ID");
            System.out.println("4. Search for messages using recipient");
            System.out.println("5. Delete a message using message hash");
            System.out.println("6. Display full stored message report");
            System.out.println("7. Return to main menu");

            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {

                case "1":
                    manager.displayStoredSendersAndRecipients();
                    break;

                case "2":
                    manager.displayLongestStoredMessage();
                    break;

                case "3":
                    searchMessageById();
                    break;

                case "4":
                    searchMessagesByRecipient();
                    break;

                case "5":
                    deleteMessageByHash();
                    break;

                case "6":
                    manager.displayStoredMessageReport();
                    break;

                case "7":
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose from 1 to 7.");
            }
        }
    }

    // PART 3: Search for a stored message using its ID.
    private void searchMessageById() {

        System.out.print("\nEnter the message ID: ");
        String messageId = input.nextLine().trim();

        Optional<SentMessages> result =
                manager.searchByMessageId(messageId);

        if (result.isPresent()) {

            SentMessages message = result.get();

            System.out.println("\nMessage found!");
            System.out.println("Recipient: "
                    + message.getReceiver());

            System.out.println("Message: "
                    + message.getMessageText());

        } else {

            System.out.println("Message ID not found.");
        }
    }

    // PART 3: Search for stored messages by recipient.
    private void searchMessagesByRecipient() {

        System.out.print("\nEnter the recipient cellphone number: ");
        String recipient = input.nextLine().trim();

        List<SentMessages> results =
                manager.searchByRecipient(recipient);

        if (results.isEmpty()) {

            System.out.println(
                    "No messages found for this recipient.");

        } else {

            System.out.println("\nMessages found:");

            for (SentMessages message : results) {

                System.out.println("Message ID: "
                        + message.getMessageId());

                System.out.println("Recipient: "
                        + message.getReceiver());

                System.out.println("Message: "
                        + message.getMessageText());

                System.out.println("--------------------------------");
            }
        }
    }

    // PART 3: Delete a stored message using its hash.
    private void deleteMessageByHash() {

        System.out.print("\nEnter the message hash to delete: ");
        String messageHash = input.nextLine().trim();

        boolean deleted = manager.deleteByHash(messageHash);

        if (deleted) {

            System.out.println(
                    "Message successfully deleted.");

        } else {

            System.out.println(
                    "Message could not be deleted. "
                    + "Check the hash and try again.");
        }
    }

    // Allows tests or other classes to access the message manager.
    public ManageMessages getMessageManager() {
        return manager;
    }
}