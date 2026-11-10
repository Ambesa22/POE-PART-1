/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationtoapp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*

/**
 *
 * @author Student
 */

        
public class ManageMessages {


    private ManageMessages manager;

    private SentMessages message1;
    private SentMessages message2;
    private SentMessages message3;
    private SentMessages message4;
    private SentMessages message5;

    @BeforeEach
    void setUp() {

        manager = new ManageMessages();

        // Test message 1: Sent
        message1 = new SentMessages(
                "Developer",
                "+27834557896",
                "Did you get the cake?",
                0
        );

        // Test message 2: Stored
        message2 = new SentMessages(
                "Developer",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                1
        );

        // Test message 3: Disregarded
        message3 = new SentMessages(
                "Developer",
                "+27834484567",
                "Yohoooo, I am at your gate.",
                2
        );

        // Test message 4: Sent
        message4 = new SentMessages(
                "Developer",
                "0838884567",
                "It is dinner time !",
                3
        );

        // Test message 5: Stored
        message5 = new SentMessages(
                "Developer",
                "+27838884567",
                "Ok, I am leaving without you.",
                4
        );

        manager.addSentMessage(message1);
        manager.addStoredMessage(message2);
        manager.addDisregardedMessage(message3);
        manager.addSentMessage(message4);
        manager.addStoredMessage(message5);
    }

    // Test 1: Sent messages array correctly populated
    @Test
    void testSentMessagesArray() {

        List<SentMessages> messages =
                manager.getSentMessages();

        assertEquals(2, messages.size());

        assertEquals(
                "Did you get the cake?",
                messages.get(0).getMessageText()
        );

        assertEquals(
                "It is dinner time !",
                messages.get(1).getMessageText()
        );
    }

    // Test 2: Display the longest stored message
    @Test
    void testLongestStoredMessage() {

        SentMessages longest =
                manager.getLongestStoredMessage().orElseThrow();

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                longest.getMessageText()
        );
    }

    // Test 3: Search by message ID
    @Test
    void testSearchByMessageId() {

        SentMessages found = manager
                .searchByMessageId(message4.getMessageId())
                .orElse(null);

        // Message 4 is sent, not stored, so it should not be found.
        assertNull(found);

        SentMessages stored = manager
                .searchByMessageId(message2.getMessageId())
                .orElse(null);

        assertNotNull(stored);
        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                stored.getMessageText()
        );
    }

    // Test 4: Search messages for a particular recipient
    @Test
    void testSearchByRecipient() {

        List<SentMessages> results =
                manager.searchByRecipient("+27838884567");

        assertEquals(2, results.size());

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                results.get(0).getMessageText()
        );

        assertEquals(
                "Ok, I am leaving without you.",
                results.get(1).getMessageText()
        );
    }

    // Test 5: Delete a message using its hash
    @Test
    void testDeleteMessageUsingHash() {

        String hash = message2.getMessageHash();

        assertTrue(manager.deleteByHash(hash));

        assertEquals(1, manager.getStoredMessages().size());

        assertFalse(
                manager.searchByMessageId(message2.getMessageId()).isPresent()
        );
    }

    // Test 6: Display report contains full message details
    @Test
    void testStoredMessageReportData() {

        List<SentMessages> stored =
                manager.getStoredMessages();

        assertEquals(2, stored.size());

        assertEquals(
                message2.getMessageHash(),
                stored.get(0).getMessageHash()
        );

        assertEquals(
                "+27838884567",
                stored.get(0).getReceiver()
        );

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                stored.get(0).getMessageText()
        );
    }

    // Additional test: Disregarded messages
    @Test
    void testDisregardedMessagesArray() {

        assertEquals(1, manager.getDisregardedMessages().size());

        assertEquals(
                "Yohoooo, I am at your gate.",
                manager.getDisregardedMessages()
                        .get(0).getMessageText()
        );
    }

    // Additional test: Hash and ID arrays
    @Test
    void testMessageHashAndIdCollections() {

        assertEquals(5, manager.getMessageHashes().size());
        assertEquals(5, manager.getMessageIds().size());
    }
}
   
}
