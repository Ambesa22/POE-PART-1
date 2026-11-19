/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registrationtoapp;


import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class ManageMessagesTest {
    


    @TempDir
    Path temporaryFolder;

    private ManageMessages manager;
    private Path testJsonFile;

    // Create a separate JSON file before each test.
    @BeforeEach
    public void setUp() {

        testJsonFile = temporaryFolder.resolve("test-messages.json");

        manager = new ManageMessages(testJsonFile);
    }

    // Helper method to create test messages.
    private SentMessages createMessage(
            String sender,
            String recipient,
            String messageText,
            int messageNumber) {

        return new SentMessages(
                sender,
                recipient,
                messageText,
                messageNumber
        );
    }

    // ==========================================
    // PART 2: SENT AND DISREGARDED MESSAGE TESTS
    // ==========================================

    @Test
    public void testAddSentMessage() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "Hello, how are you?",
                1
        );

        manager.addSentMessage(message);

        assertEquals(1, manager.getSentMessages().size());

        assertEquals(
                "Hello, how are you?",
                manager.getSentMessages().get(0).getMessageText()
        );
    }

    @Test
    public void testAddDisregardedMessage() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "This message will be disregarded.",
                1
        );

        manager.addDisregardedMessage(message);

        assertEquals(1, manager.getDisregardedMessages().size());
    }

    @Test
    public void testAddNullSentMessage() {

        manager.addSentMessage(null);

        assertTrue(manager.getSentMessages().isEmpty());
    }

    @Test
    public void testAddNullDisregardedMessage() {

        manager.addDisregardedMessage(null);

        assertTrue(manager.getDisregardedMessages().isEmpty());
    }

    // ==========================================
    // PART 3: STORE AND LOAD JSON TESTS
    // ==========================================

    @Test
    public void testStoreMessageSuccessfully() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "Hello, how are you?",
                1
        );

        assertTrue(manager.addStoredMessage(message));

        assertEquals(1, manager.getStoredMessages().size());
    }

    @Test
    public void testStoredMessageSavedToJsonFile() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "This message must be saved.",
                1
        );

        assertTrue(manager.addStoredMessage(message));

        assertTrue(Files.exists(testJsonFile));
    }

    @Test
    public void testLoadStoredMessagesFromJsonFile() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "This message must be loaded.",
                1
        );

        assertTrue(manager.addStoredMessage(message));

        ManageMessages newManager =
                new ManageMessages(testJsonFile);

        assertEquals(1, newManager.getStoredMessages().size());

        SentMessages loadedMessage =
                newManager.getStoredMessages().get(0);

        assertEquals(
                message.getMessageId(),
                loadedMessage.getMessageId()
        );

        assertEquals(
                message.getMessageHash(),
                loadedMessage.getMessageHash()
        );

        assertEquals(
                message.getMessageText(),
                loadedMessage.getMessageText()
        );

        assertEquals(
                message.getSender(),
                loadedMessage.getSender()
        );

        assertEquals(
                message.getReceiver(),
                loadedMessage.getReceiver()
        );
    }

    @Test
    public void testAddNullStoredMessage() {

        assertFalse(manager.addStoredMessage(null));

        assertTrue(manager.getStoredMessages().isEmpty());
    }

    // ==========================================
    // PART 3: MESSAGE ID AND HASH TESTS
    // ==========================================

    @Test
    public void testGetMessageIds() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "Testing message ID.",
                1
        );

        manager.addStoredMessage(message);

        List<String> ids = manager.getMessageIds();

        assertEquals(1, ids.size());

        assertEquals(message.getMessageId(), ids.get(0));

        assertTrue(ids.get(0).matches("\\d{10}"));
    }

    @Test
    public void testGetMessageHashes() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "Testing message hash.",
                1
        );

        manager.addStoredMessage(message);

        List<String> hashes = manager.getMessageHashes();

        assertEquals(1, hashes.size());

        assertEquals(message.getMessageHash(), hashes.get(0));

        assertFalse(hashes.get(0).isEmpty());
    }

    // ==========================================
    // PART 3: DISPLAY STORED MESSAGE INFORMATION
    // ==========================================

    @Test
    public void testDisplayStoredSendersAndRecipients() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "Hello Keegan.",
                1
        );

        manager.addStoredMessage(message);

        assertEquals(
                "Mike Smith",
                manager.getStoredMessages().get(0).getSender()
        );

        assertEquals(
                "0821234567",
                manager.getStoredMessages().get(0).getReceiver()
        );

        assertDoesNotThrow(
                () -> manager.displayStoredSendersAndRecipients()
        );
    }

    // ==========================================
    // PART 3: LONGEST MESSAGE TESTS
    // ==========================================

    @Test
    public void testGetLongestStoredMessage() {

        SentMessages shortMessage = createMessage(
                "Mike Smith",
                "0821234567",
                "Hello.",
                1
        );

        SentMessages longMessage = createMessage(
                "Mike Smith",
                "0821234567",
                "This is the longest stored message.",
                2
        );

        manager.addStoredMessage(shortMessage);
        manager.addStoredMessage(longMessage);

        Optional<SentMessages> result =
                manager.getLongestStoredMessage();

        assertTrue(result.isPresent());

        assertEquals(
                "This is the longest stored message.",
                result.get().getMessageText()
        );
    }

    @Test
    public void testLongestMessageWhenNoMessagesAreStored() {

        Optional<SentMessages> result =
                manager.getLongestStoredMessage();

        assertFalse(result.isPresent());
    }

    // ==========================================
    // PART 3: SEARCH BY MESSAGE ID TESTS
    // ==========================================

    @Test
    public void testSearchByMessageId() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "Find this message.",
                1
        );

        manager.addStoredMessage(message);

        Optional<SentMessages> result =
                manager.searchByMessageId(message.getMessageId());

        assertTrue(result.isPresent());

        assertEquals(
                "Find this message.",
                result.get().getMessageText()
        );
    }

    @Test
    public void testSearchByInvalidMessageId() {

        Optional<SentMessages> result =
                manager.searchByMessageId("0000000000");

        assertFalse(result.isPresent());
    }

    @Test
    public void testSearchByNullMessageId() {

        Optional<SentMessages> result =
                manager.searchByMessageId(null);

        assertFalse(result.isPresent());
    }

    // ==========================================
    // PART 3: SEARCH BY RECIPIENT TESTS
    // ==========================================

    @Test
    public void testSearchByRecipient() {

        SentMessages message1 = createMessage(
                "Mike Smith",
                "0821234567",
                "First message.",
                1
        );

        SentMessages message2 = createMessage(
                "Mike Smith",
                "0821234567",
                "Second message.",
                2
        );

        SentMessages message3 = createMessage(
                "Mike Smith",
                "0831234567",
                "Another recipient.",
                3
        );

        manager.addStoredMessage(message1);
        manager.addStoredMessage(message2);
        manager.addStoredMessage(message3);

        List<SentMessages> results =
                manager.searchByRecipient("0821234567");

        assertEquals(2, results.size());
    }

    @Test
    public void testSearchByRecipientNotFound() {

        List<SentMessages> results =
                manager.searchByRecipient("0841234567");

        assertTrue(results.isEmpty());
    }

    // ==========================================
    // PART 3: DELETE MESSAGE USING HASH
    // ==========================================

    @Test
    public void testDeleteMessageByHash() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "Delete this message.",
                1
        );

        manager.addStoredMessage(message);

        boolean deleted =
                manager.deleteByHash(message.getMessageHash());

        assertTrue(deleted);

        assertTrue(manager.getStoredMessages().isEmpty());
    }

    @Test
    public void testDeleteMessageWithInvalidHash() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "Keep this message.",
                1
        );

        manager.addStoredMessage(message);

        boolean deleted =
                manager.deleteByHash("INVALID-HASH");

        assertFalse(deleted);

        assertEquals(1, manager.getStoredMessages().size());
    }

    @Test
    public void testDeleteMessageWithNullHash() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "Keep this message.",
                1
        );

        manager.addStoredMessage(message);

        assertFalse(manager.deleteByHash(null));

        assertEquals(1, manager.getStoredMessages().size());
    }

    // ==========================================
    // PART 3: FULL MESSAGE REPORT TEST
    // ==========================================

    @Test
    public void testDisplayStoredMessageReport() {

        SentMessages message = createMessage(
                "Mike Smith",
                "0821234567",
                "This is a report test.",
                1
        );

        manager.addStoredMessage(message);

        assertDoesNotThrow(
                () -> manager.displayStoredMessageReport()
        );
    }

    // ==========================================
    // PART 2 AND PART 3: COLLECTION TEST
    // ==========================================

    @Test
    public void testAllMessageCollections() {

        SentMessages sentMessage = createMessage(
                "Mike Smith",
                "0821234567",
                "Sent message.",
                1
        );

        SentMessages disregardedMessage = createMessage(
                "Mike Smith",
                "0821234567",
                "Disregarded message.",
                2
        );

        SentMessages storedMessage = createMessage(
                "Mike Smith",
                "0821234567",
                "Stored message.",
                3
        );

        manager.addSentMessage(sentMessage);
        manager.addDisregardedMessage(disregardedMessage);

        assertTrue(manager.addStoredMessage(storedMessage));

        assertEquals(1, manager.getSentMessages().size());

        assertEquals(1, manager.getDisregardedMessages().size());

        assertEquals(1, manager.getStoredMessages().size());
    }
}
