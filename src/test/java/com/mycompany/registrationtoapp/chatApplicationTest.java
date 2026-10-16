/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registrationtoapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class chatApplicationTest {
    
   


public class QuickChatAppTest {

    @Test
    public void testValidMessage() {
        assertTrue(chatApplication.isValidMessage("Hello there"));
    }

    @Test
    public void testMessageOf250Characters() {
        assertTrue(chatApplication.isValidMessage("A".repeat(250)));
    }

    @Test
    public void testMessageOf251Characters() {
        assertFalse(chatApplication.isValidMessage("A".repeat(251)));
    }

    @Test
    public void testEmptyMessage() {
        assertFalse(chatApplication.isValidMessage(""));
    }

    @Test
    public void testValidSouthAfricanRecipient() {
        assertTrue(
            chatApplication.isValidRecipient("+27821234567")
        );
    }

    @Test
    public void testInvalidSouthAfricanRecipient() {
        assertFalse(
            chatApplication.isValidRecipient("0821234567")
        );
    }

    @Test
    public void testMessageHash() {
        SentMessages message = new SentMessages(
            "+27821234567", "Hi Thanks", 0
        );

        assertEquals(
            message.getMessageId().substring(0, 2)
                + ":0:HITHANKS",
            message.getMessageHash()
        );
    }

    @Test
    public void testMessageIdHasTenDigits() {
        SentMessages message = new SentMessages(
            "+27821234567", "Hello", 0
        );

        assertTrue(message.getMessageId().matches("\\d{10}"));
    }

    @Test
    public void testMessageDetails() {
        SentMessages message = new SentMessages(
            "+27821234567", "Hello there", 0
        );

        assertEquals("+27821234567", message.getRecipient());
        assertEquals("Hello there", message.getMessageText());
    }
}

    }
    

