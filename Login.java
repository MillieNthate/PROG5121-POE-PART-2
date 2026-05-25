/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp1;

/**
 *
 * @author millie
 */
import java.util.regex.Pattern;

public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellphone;

    // Username validation
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Password validation
    public boolean checkPasswordComplexity(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(regex, password);
    }

    // Cell phone validation
    public boolean checkCellPhoneNumber(String cellphone) {
        String regex = "^\\+27\\d{9}$";
        return Pattern.matches(regex, cellphone);
    }

    // Register user
    public String registerUser(String firstName, String lastName,
                               String username, String password, String cellphone) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellphone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellphone = cellphone;

        return "Username successfully captured.\n"
             + "Password successfully captured.\n"
             + "Cell phone number successfully added.";
    }

    // Login validation
    public boolean loginUser(String username, String password) {

        if (this.username == null || this.password == null) {
            return false;
        }

        return this.username.equals(username) && this.password.equals(password);
    }

    // Login message
    public String returnLoginStatus(boolean status) {
        if (status) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
