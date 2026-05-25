/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp1;

/**
 *
 * @author millie
 */

import java.util.Scanner;

public class Chatapp1 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login = new Login();

        // REGISTER
        System.out.println("=== REGISTER ===");
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        System.out.print("Enter cell phone (+27...): ");
        String phone = input.nextLine();

        String result = login.registerUser(firstName, lastName, username, password, phone);
        System.out.println(result);

        // LOGIN
        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter username: ");
        String loginUser = input.nextLine();
        System.out.print("Enter password: ");
        String loginPass = input.nextLine();

        boolean status = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(status));

        if (!status) {
            System.out.println("Login failed. Exiting.");
            input.close();
            return;
        }

        // ========== MESSAGING (PART 2) ==========
        System.out.println("\n=== QUICKCHAT MESSAGING ===");
        System.out.print("How many messages would you like to send? ");
        int numMessages = Integer.parseInt(input.nextLine());

        int messagesCreated = 0;
        Message currentMsg = null;

        while (messagesCreated < numMessages) {
            System.out.println("\n--- New Message " + (messagesCreated + 1) + " ---");
            System.out.print("Recipient cell number (e.g., +27718693002): ");
            String recipient = input.nextLine();
            System.out.print("Message (max 250 characters): ");
            String msgText = input.nextLine();

            // Validate message length
            if (msgText.length() > 250) {
                int excess = msgText.length() - 250;
                System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                continue;
            }

            // Create Message object
            currentMsg = new Message(recipient, msgText, messagesCreated + 1);

            // Validate recipient cell
            String cellCheck = currentMsg.checkRecipientCell();
            System.out.println(cellCheck);
            if (cellCheck.contains("incorrectly formatted")) {
                continue;
            }

            // Ask what to do
            System.out.println("Options: 1) Send Message  2) Disregard Message  3) Store Message to send later");
            System.out.print("Your choice: ");
            int action = Integer.parseInt(input.nextLine());

            if (action == 1) {
                System.out.println(currentMsg.sentMessage(1));
                System.out.println("\n--- Message Sent ---");
                System.out.println("Message ID: " + currentMsg.getMessageID());
                System.out.println("Message Hash: " + currentMsg.getMessageHash());
                System.out.println("Recipient: " + currentMsg.getRecipient());
                System.out.println("Message: " + currentMsg.getMessageText());
            } else if (action == 2) {
                System.out.println(currentMsg.sentMessage(2));
            } else if (action == 3) {
                System.out.println(currentMsg.sentMessage(3));
            } else {
                System.out.println("Invalid action. Message discarded.");
            }

            messagesCreated++;
        }

        if (currentMsg != null) {
            System.out.println("\nTotal number of messages sent this session: " + currentMsg.returnTotalMessages());
        } else {
            System.out.println("\nNo messages were created.");
        }

        input.close();
    }
}
