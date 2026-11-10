/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationtoapp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Student
 */
public class ManageMessages {
  private final Path jsonFile;

    // Gson converts Java objects to and from JSON.
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // PART 2: Collections for managing messages.
    private final List<SentMessages> sentMessages =
            new ArrayList<>();

    private final List<SentMessages> disregardedMessages =
            new ArrayList<>();

    private final List<SentMessages> storedMessages =
            new ArrayList<>();

    // Default constructor.
    public ManageMessages() {
        this(Path.of("messages.json"));
    }

    // Constructor used for testing with a separate JSON file.
    public ManageMessages(Path jsonFile) {
        this.jsonFile = jsonFile;
        loadStoredMessages();
    }

    // PART 2: Add a successfully sent message.
    public void addSentMessage(SentMessages message) {

        if (message != null) {
            sentMessages.add(message);
        }
    }

    // PART 2: Add a disregarded message.
    public void addDisregardedMessage(SentMessages message) {

        if (message != null) {
            disregardedMessages.add(message);
        }
    }

    // PART 2 AND PART 3: Store a message and save it to JSON.
    public boolean addStoredMessage(SentMessages message) {

        if (message == null) {
            return false;
        }

        storedMessages.add(message);

        if (saveStoredMessages()) {
            return true;
        }

        // Remove the message if saving fails.
        storedMessages.remove(message);
        return false;
    }

    // PART 3: Save stored messages to a JSON file.
    public boolean saveStoredMessages() {

        try {
            Path parent = jsonFile.toAbsolutePath().getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            String json = gson.toJson(storedMessages);

            Files.writeString(jsonFile, json);

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error saving messages: "
                    + exception.getMessage());

            return false;
        }
    }

    // PART 3: Load stored messages from the JSON file.
    public void loadStoredMessages() {

        try {

            if (!Files.exists(jsonFile)) {
                return;
            }

            String json = Files.readString(jsonFile);

            if (json.trim().isEmpty()) {
                return;
            }

            Type messageListType =
                    new TypeToken<ArrayList<SentMessages>>() {
                    }.getType();

            List<SentMessages> loadedMessages =
                    gson.fromJson(json, messageListType);

            if (loadedMessages != null) {
                storedMessages.clear();
                storedMessages.addAll(loadedMessages);
            }

        } catch (IOException | RuntimeException exception) {

            System.out.println(
                    "Error loading stored messages: "
                    + exception.getMessage());
        }
    }

    // PART 2: Return all sent messages.
    public List<SentMessages> getSentMessages() {

        return Collections.unmodifiableList(sentMessages);
    }

    // PART 2: Return all disregarded messages.
    public List<SentMessages> getDisregardedMessages() {

        return Collections.unmodifiableList(disregardedMessages);
    }

    // PART 2 AND PART 3: Return all stored messages.
    public List<SentMessages> getStoredMessages() {

        return Collections.unmodifiableList(storedMessages);
    }

    // PART 3: Return all message hashes.
    public List<String> getMessageHashes() {

        List<String> hashes = new ArrayList<>();

        for (SentMessages message : storedMessages) {
            hashes.add(message.getMessageHash());
        }

        return hashes;
    }

    // PART 3: Return all message IDs.
    public List<String> getMessageIds() {

        List<String> ids = new ArrayList<>();

        for (SentMessages message : storedMessages) {
            ids.add(message.getMessageId());
        }

        return ids;
    }

    // PART 3: Display the sender and recipient
    // of every stored message.
    public void displayStoredSendersAndRecipients() {

        if (storedMessages.isEmpty()) {
            System.out.println("There are no stored messages.");
            return;
        }

        System.out.println("\n===== STORED MESSAGE SENDERS AND RECIPIENTS =====");

        for (SentMessages message : storedMessages) {

            System.out.println("Sender: "
                    + message.getSender());

            System.out.println("Recipient: "
                    + message.getReceiver());

            System.out.println("--------------------------------");
        }
    }

    // PART 3: Find the longest stored message.
    public Optional<SentMessages> getLongestStoredMessage() {

        return storedMessages.stream()
                .max((message1, message2) ->
                        Integer.compare(
                                message1.getMessageText().length(),
                                message2.getMessageText().length()));
    }

    // PART 3: Display the longest stored message.
    public void displayLongestStoredMessage() {

        Optional<SentMessages> longest =
                getLongestStoredMessage();

        if (longest.isPresent()) {

            SentMessages message = longest.get();

            System.out.println("\n===== LONGEST STORED MESSAGE =====");

            System.out.println("Message: "
                    + message.getMessageText());

            System.out.println("Length: "
                    + message.getMessageText().length()
                    + " characters");

            System.out.println("Recipient: "
                    + message.getReceiver());

        } else {

            System.out.println("There are no stored messages.");
        }
    }

    // PART 3: Search for a message using its message ID.
    public Optional<SentMessages> searchByMessageId(
            String messageId) {

        if (messageId == null) {
            return Optional.empty();
        }

        return storedMessages.stream()
                .filter(message ->
                        message.getMessageId().equals(messageId))
                .findFirst();
    }

    // PART 3: Search for all messages sent to a recipient.
    public List<SentMessages> searchByRecipient(
            String recipient) {

        List<SentMessages> results = new ArrayList<>();

        if (recipient == null) {
            return results;
        }

        for (SentMessages message : storedMessages) {

            if (message.getReceiver().equalsIgnoreCase(
                    recipient.trim())) {

                results.add(message);
            }
        }

        return results;
    }

    // PART 3: Delete a stored message using its message hash.
    public boolean deleteByHash(String messageHash) {

        if (messageHash == null) {
            return false;
        }

        for (int i = 0; i < storedMessages.size(); i++) {

            SentMessages message = storedMessages.get(i);

            if (message.getMessageHash().equalsIgnoreCase(
                    messageHash.trim())) {

                // Temporarily remove the message.
                storedMessages.remove(i);

                // Save the updated list.
                if (saveStoredMessages()) {
                    return true;
                }

                // Restore the message if saving fails.
                storedMessages.add(i, message);

                return false;
            }
        }

        return false;
    }

    // PART 3: Display a full report of stored messages.
    public void displayStoredMessageReport() {

        if (storedMessages.isEmpty()) {

            System.out.println("There are no stored messages.");
            return;
        }

        System.out.println("\n========== STORED MESSAGE REPORT ==========");

        for (SentMessages message : storedMessages) {

            System.out.println("Message Hash: "
                    + message.getMessageHash());

            System.out.println("Recipient: "
                    + message.getReceiver());

            System.out.println("Message: "
                    + message.getMessageText());

            System.out.println("-------------------------------------------");
        }

        System.out.println("Total stored messages: "
                + storedMessages.size());

        System.out.println("===========================================");
    }
}    