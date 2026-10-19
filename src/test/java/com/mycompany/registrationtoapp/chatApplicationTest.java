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



    @Test
    void testValidMessage() {
        String message = "Hello, how are you?";
        assertTrue(chatApplication.isValidMessage(message));
    }

    @Test
    void testMessageExactly250Characters() {
        String message = "A".repeat(250);
        assertTrue(chatApplication.isValidMessage(message));
    }

    @Test
    void testMessageOver250Characters() {
        String message = "A".repeat(251);
        assertFalse(chatApplication.isValidMessage(message));
    }

    @Test
    void testEmptyMessageIsInvalid() {
        assertFalse(chatApplication.isValidMessage(""));
    }

    @Test
    void testWhitespaceMessageIsInvalid() {
        assertFalse(chatApplication.isValidMessage("   "));
    }

    @Test
    void testValidSouthAfricanRecipient() {
        assertTrue(chatApplication.isValidRecipient("+27821234567"));
    }

    @Test
    void testRecipientWithoutCountryCode() {
        assertFalse(chatApplication.isValidRecipient("0821234567"));
    }

    @Test
    void testRecipientContainingLetters() {
        assertFalse(chatApplication.isValidRecipient("+2782ABC4567"));
    }

    @Test
    void testMessageIdContainsTenDigits() {
        SentMessages message =
                new SentMessages("+27821234567", "Hello Thanks", 0);

        assertTrue(message.getMessageId().matches("\\d{10}"));
    }

    @Test
    void testMessageHashFormat() {
        SentMessages message =
                new SentMessages("+27821234567", "Hi Thanks", 0);

        String expected = message.getMessageId().substring(0, 2)
                + ":0:HITHANKS";

        assertEquals(expected, message.getMessageHash());
    }

    @Test
    void testReceiverGetter() {
        SentMessages message =
                new SentMessages("+27821234567", "Hello", 1);

        assertEquals("+27821234567", message.getReceiver());
    }

    @Test
    void testMessageTextGetter() {
        SentMessages message =
                new SentMessages("+27821234567", "Hello World", 1);

        assertEquals("Hello World", message.getMessageText());
    }

    @Test
    void testMessageNumberGetter() {
        SentMessages message =
                new SentMessages("+27821234567", "Hello", 5);

        assertEquals(5, message.getMessageNumber());
    }

    @Test
    void testConstructorRejectsEmptyMessage() {
        assertThrows(IllegalArgumentException.class, () ->
                new SentMessages("+27821234567", "", 0));
    }


    @Test
    void testInitialTotalMessagesIsZero() {
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(new byte[0]));

        chatApplication app = new chatApplication(scanner);

        assertEquals(0, app.returnTotalMessages());

        scanner.close();
    }

    @Test
    void testToStringContainsMessageDetails() {
        SentMessages message =
                new SentMessages("+27821234567", "Hello World", 1);

        assertTrue(message.toString().contains(message.getMessageId()));
        assertTrue(message.toString().contains(message.getMessageHash()));
        assertTrue(message.toString().contains("+27821234567"));
        assertTrue(message.toString().contains("Hello World"));
    }
}
