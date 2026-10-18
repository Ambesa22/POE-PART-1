/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registrationtoapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class chatApplicationTest {

    // Test 1: Valid message
    @Test
    public void testValidMessage() {
        assertTrue(
            chatApplication.isValidMessage("Hello, how are you?")
        );
    }

    // Test 2: Message with exactly 250 characters
    @Test
    public void testMessageOf250Characters() {
        String message = "A".repeat(250);

        assertTrue(chatApplication.isValidMessage(message));
    }

    // Test 3: Message exceeding 250 characters
    @Test
    public void testMessageExceeds250Characters() {
        String message = "A".repeat(251);

        assertFalse(chatApplication.isValidMessage(message));
    }

    // Test 4: Empty message
    @Test
    public void testEmptyMessage() {
        assertFalse(chatApplication.isValidMessage(""));
    }

    // Test 5: Message containing only spaces
    @Test
    public void testMessageContainingOnlySpaces() {
        assertFalse(chatApplication.isValidMessage("   "));
    }

    // Test 6: Valid South African recipient
    @Test
    public void testValidRecipient() {
        assertTrue(
            chatApplication.isValidRecipient("+27821234567")
        );
    }

    // Test 7: Recipient without international code
    @Test
    public void testRecipientWithoutCountryCode() {
        assertFalse(
            chatApplication.isValidRecipient("0821234567")
        );
    }

    // Test 8: Recipient containing letters
    @Test
    public void testRecipientContainingLetters() {
        assertFalse(
            chatApplication.isValidRecipient("+27ABC123456")
        );
    }

    // Test 9: Generate a ten-digit message ID
    @Test
    public void testMessageIdContainsTenDigits() {
        SentMessages message = new SentMessages(
            "+27821234567", "Hello there", 0
        );

        assertTrue(
            message.getMessageId().matches("\\d{10}")
        );
    }

    // Test 10: Generate the correct message hash format
    @Test
    public void testMessageHash() {
        SentMessages message = new SentMessages(
            "+27821234567", "Hi Thanks", 0
        );

        String expectedHash =
            message.getMessageId().substring(0, 2)
            + ":0:HITHANKS";

        assertEquals(expectedHash, message.getMessageHash());
    }

    // Test 11: Check the receiver
    @Test
    public void testGetReceiver() {
        SentMessages message = new SentMessages(
            "+27821234567", "Hello", 0
        );

        assertEquals("+27821234567", message.getReceiver());
    }

    // Test 12: Check the message text
    @Test
    public void testGetMessageText() {
        SentMessages message = new SentMessages(
            "+27821234567", "Hello there", 0
        );

        assertEquals("Hello there", message.getMessageText());
    }

    // Test 13: Check the message number
    @Test
    public void testGetMessageNumber() {
        SentMessages message = new SentMessages(
            "+27821234567", "Hello", 2
        );

        assertEquals(2, message.getMessageNumber());
    }

    // Test 14: Reject an empty message in SentMessages
    @Test
    public void testRejectEmptyMessageInConstructor() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new SentMessages(
                "+27821234567", "   ", 0
            )
        );
    }

    // Test 15: Reject a message longer than 250 characters
    @Test
    public void testRejectLongMessageInConstructor() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new SentMessages(
                "+27821234567", "A".repeat(251), 0
            )
        );
    }

    // Test 16: Total sent messages starts at zero
    @Test
    public void testInitialTotalMessagesSent() {
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(
                new byte[0]
            )
        );

        chatApplication app = new chatApplication(scanner);

        assertEquals(0, app.returnTotalMessages());

        scanner.close();
    }

    // Test 17: Check message details in toString()
    @Test
    public void testMessageToString() {
        SentMessages message = new SentMessages(
            "+27821234567", "Hello there", 0
        );

        String output = message.toString();

        assertTrue(output.contains("Message ID:"));
        assertTrue(output.contains("Message Hash:"));
        assertTrue(output.contains("Receiver: +27821234567"));
        assertTrue(output.contains("Message: Hello there"));
    }
}
