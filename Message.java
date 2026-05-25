/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp1;

/**
 *
 * @author millie
 */


import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {

    private static int totalMessagesSent = 0;
    private static final List<Message> allMessages = new ArrayList<>();

    private String messageID;
    private int numSent;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;

    // Constructor
    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.numSent = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash(messageNumber);
        this.status = "pending";
    }

    // Validate message ID
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    // Validate recipient number using Login class
    public String checkRecipientCell() {
        Login loginValidator = new Login();

        if (loginValidator.checkCellPhoneNumber(recipient)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // FIXED HASH METHOD (IMPORTANT)
    public String createMessageHash(int messageNumber) {
        String firstTwo = messageID.substring(0, 2);

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");

        String hash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;

        return hash.toUpperCase();
    }

    // Message actions
    public String sentMessage(int choice) {

        if (choice == 1) {
            status = "sent";
            totalMessagesSent++;
            allMessages.add(this);
            storeMessageInJSON();
            return "Message successfully sent.";

        } else if (choice == 2) {
            status = "disregarded";
            return "Press 0 to delete the message.";

        } else if (choice == 3) {
            status = "stored";
            allMessages.add(this);
            storeMessageInJSON();
            return "Message successfully stored.";

        } else {
            return "Invalid option.";
        }
    }

    // Print all messages
    public String printMessages() {
        if (allMessages.isEmpty()) {
            return "No messages have been sent or stored yet.";
        }

        StringBuilder sb = new StringBuilder();

        for (Message m : allMessages) {
            sb.append("Message ID: ").append(m.messageID)
              .append(", Hash: ").append(m.messageHash)
              .append(", Recipient: ").append(m.recipient)
              .append(", Message: ").append(m.messageText)
              .append("\n");
        }

        return sb.toString();
    }

    // Total sent messages
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Store to JSON file
    private void storeMessageInJSON() {
        try (FileWriter fw = new FileWriter("messages.json", true)) {

            String json = String.format(
                "{\"messageID\":\"%s\",\"numSent\":%d,\"recipient\":\"%s\",\"message\":\"%s\",\"hash\":\"%s\",\"status\":\"%s\",\"timestamp\":\"%s\"}\n",
                messageID,
                numSent,
                recipient,
                messageText.replace("\"", "\\\""),
                messageHash,
                status,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );

            fw.write(json);

        } catch (IOException e) {
            System.err.println("Error storing message in JSON: " + e.getMessage());
        }
    }

    // Generate 10-digit ID
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1_000_000_000L + (long)(rand.nextDouble() * 9_000_000_000L);
        return String.valueOf(id);
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getStatus() { return status; }
    public int getNumSent() { return numSent; }

    // Message length validation (USED IN TESTS)
    public String checkMessageLength() {
        if (this.messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = this.messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
}
