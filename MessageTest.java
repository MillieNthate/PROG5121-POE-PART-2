/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.chatapp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    public Message message;

    @BeforeEach
    public void setUp() {
        message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                1
        );
    }

    // ---------------- MESSAGE LENGTH ----------------

    @Test
    public void testMessageLengthSuccess() {
        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageLengthFailure() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append("a");

        Message longMessage = new Message(
                "+27718693002",
                sb.toString(),
                2
        );

        assertTrue(
                longMessage.checkMessageLength().contains("Message exceeds 250 characters")
        );
    }

    // ---------------- RECIPIENT ----------------

    @Test
    public void testRecipientCorrectlyFormatted() {
        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testRecipientIncorrectlyFormatted() {
        Message invalid = new Message(
                "08575975889",
                "Hi",
                2
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                invalid.checkRecipientCell()
        );
    }

    // ---------------- MESSAGE HASH ----------------

    @Test
    public void testMessageHashCorrect() {
        String idFirstTwo = message.getMessageID().substring(0, 2);

        assertEquals(
                (idFirstTwo + ":1:HITONIGHT").toUpperCase(),
                message.getMessageHash()
        );
    }

    // ---------------- MESSAGE ID ----------------

    @Test
    public void testMessageIDCreated() {
        assertNotNull(message.getMessageID());
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.getMessageID().matches("\\d{10}"));
    }

    // ---------------- SENDING ----------------

    @Test
    public void testSendMessage() {
        assertEquals("Message successfully sent.", message.sentMessage(1));
        assertEquals("sent", message.getStatus());
    }

    @Test
    public void testDisregardMessage() {
        assertEquals("Press 0 to delete the message.", message.sentMessage(2));
        assertEquals("disregarded", message.getStatus());
    }

    @Test
    public void testStoreMessage() {
        assertEquals("Message successfully stored.", message.sentMessage(3));
        assertEquals("stored", message.getStatus());
    }

    // ---------------- TOTAL MESSAGES ----------------

    @Test
    public void testTotalMessagesAccumulated() {
        Message m1 = new Message("+27718693002", "One", 1);
        m1.sentMessage(1);

        Message m2 = new Message("+27718693002", "Two", 2);
        m2.sentMessage(1);

        assertEquals(2, m2.returnTotalMessages());
    }
}