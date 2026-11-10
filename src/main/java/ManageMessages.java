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
  private final List<SentMessages> sentMessages = new ArrayList<>();
    private final List<SentMessages> disregardedMessages =
            new ArrayList<>();
    private final List<SentMessages> storedMessages =
            new ArrayList<>();

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Path jsonFile = Path.of("messages.json");

    // Load previously stored messages from JSON
    public void loadStoredMessages() {

        if (!Files.exists(jsonFile)) {
            return;
        }

        try {
            String json = Files.readString(jsonFile);

            Type messageListType =
                    new TypeToken<ArrayList<SentMessages>>() {}.getType();

            List<SentMessages> loaded =
                    gson.fromJson(json, messageListType);

            storedMessages.clear();

            if (loaded != null) {
                storedMessages.addAll(loaded);
            }

        } catch (IOException | RuntimeException e) {
            System.out.println("Could not load stored messages: "
                    + e.getMessage());
        }
    }

    // Add a successfully sent message
    public void addSentMessage(SentMessages message) {
        sentMessages.add(message);
    }

    // Add a disregarded message
    public void addDisregardedMessage(SentMessages message) {
        disregardedMessages.add(message);
    }

    // Store message and save JSON file
    public void addStoredMessage(SentMessages message) {

        storedMessages.add(message);

        try {
            saveStoredMessages();
        } catch (IOException e) {
            storedMessages.remove(message);
            System.out.println("Could not save message: "
                    + e.getMessage());
        }
    }

    // Write stored messages to JSON
    private void saveStoredMessages() throws IOException {

        String json = gson.toJson(storedMessages);

        Files.writeString(jsonFile, json);
    }

    // Return all five collections
    public List<SentMessages> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }

    public List<SentMessages> getDisregardedMessages() {
        return new ArrayList<>(disregardedMessages);
    }

    public List<SentMessages> getStoredMessages() {
        return new ArrayList<>(storedMessages);
    }

    public List<String> getMessageHashes() {
        List<String> hashes = new ArrayList<>();

        for (SentMessages message : getAllMessages()) {
            hashes.add(message.getMessageHash());
        }

        return hashes;
    }

    public List<String> getMessageIds() {
        List<String> ids = new ArrayList<>();

        for (SentMessages message : getAllMessages()) {
            ids.add(message.getMessageId());
        }

        return ids;
    }

    private List<SentMessages> getAllMessages() {
        List<SentMessages> all = new ArrayList<>();

        all.addAll(sentMessages);
        all.addAll(disregardedMessages);
        all.addAll(storedMessages);

        return all;
    }

    // 1. Display sender and recipient of stored messages
    public void displayStoredSendersAndRecipients() {

        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages available.");
            return;
        }

        for (SentMessages message : storedMessages) {
            System.out.println("Sender: " + message.getSender());
            System.out.println("Recipient: " + message.getReceiver());
            System.out.println("-------------------------");
        }
    }

    // 2. Display the longest stored message
    public Optional<SentMessages> getLongestStoredMessage() {

        return storedMessages.stream()
                .max((a, b) ->
                        Integer.compare(
                                a.getMessageText().length(),
                                b.getMessageText().length()));
    }

    public void displayLongestStoredMessage() {

        Optional<SentMessages> longest = getLongestStoredMessage();

        if (longest.isPresent()) {
            System.out.println("Longest stored message:");
            System.out.println(longest.get().getMessageText());
        } else {
            System.out.println("No stored messages available.");
        }
    }

    // 3. Search using message ID
    public Optional<SentMessages> searchByMessageId(String id) {

        return storedMessages.stream()
                .filter(message ->
                        message.getMessageId().equals(id))
                .findFirst();
    }

    // 4. Search all messages for a recipient
    public List<SentMessages> searchByRecipient(String recipient) {

        List<SentMessages> results = new ArrayList<>();

        for (SentMessages message : storedMessages) {
            if (message.getReceiver().equalsIgnoreCase(recipient)) {
                results.add(message);
            }
        }

        return results;
    }

    // 5. Delete a message using its hash
    public boolean deleteByHash(String hash) {

        for (int i = 0; i < storedMessages.size(); i++) {

            if (storedMessages.get(i).getMessageHash()
                    .equalsIgnoreCase(hash)) {

                storedMessages.remove(i);

                try {
                    saveStoredMessages();
                    return true;
                } catch (IOException e) {
                    System.out.println("Could not update JSON file: "
                            + e.getMessage());
                    return false;
                }
            }
        }

        return false;
    }

    // 6. Display complete report
    public void displayStoredMessageReport() {

        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages available.");
            return;
        }

        System.out.println("===== STORED MESSAGE REPORT =====");

        for (SentMessages message : storedMessages) {
            System.out.println(message);
            System.out.println("-------------------------");
        }
    }
}   
    

