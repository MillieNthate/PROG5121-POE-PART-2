/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapp1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */

public class LoginTest {

    @Test
    public void testCheckUserName_Valid() {
        Login instance = new Login();
        assertTrue(instance.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserName_Invalid_NoUnderscore() {
        Login instance = new Login();
        assertFalse(instance.checkUserName("kyle1"));
    }

    @Test
    public void testCheckUserName_Invalid_TooLong() {
        Login instance = new Login();
        assertFalse(instance.checkUserName("kyle_123"));
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        Login instance = new Login();
        assertTrue(instance.checkPasswordComplexity("Password1!"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid() {
        Login instance = new Login();
        assertFalse(instance.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber_Valid() {
        Login instance = new Login();
        assertTrue(instance.checkCellPhoneNumber("+27831234567"));
    }

    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        Login instance = new Login();
        assertFalse(instance.checkCellPhoneNumber("0831234567"));
    }

    @Test
    public void testRegisterUser_Success() {
        Login instance = new Login();

        String result = instance.registerUser(
                "John",
                "Doe",
                "j_doe",
                "Password1!",
                "+27831234567"
        );

        assertTrue(result.contains("successfully"));
    }

    @Test
    public void testRegisterUser_InvalidUsername() {
        Login instance = new Login();

        String result = instance.registerUser(
                "John",
                "Doe",
                "johndoe",
                "Password1!",
                "+27831234567"
        );

        assertTrue(result.contains("Username is not correctly formatted"));
    }

    @Test
    public void testLoginUser_Success() {
        Login instance = new Login();

        instance.registerUser(
                "John",
                "Doe",
                "j_doe",
                "Password1!",
                "+27831234567"
        );

        assertTrue(instance.loginUser("j_doe", "Password1!"));
    }

    @Test
    public void testLoginUser_Fail() {
        Login instance = new Login();

        instance.registerUser(
                "John",
                "Doe",
                "j_doe",
                "Password1!",
                "+27831234567"
        );

        assertFalse(instance.loginUser("wrong", "wrong"));
    }

    @Test
    public void testReturnLoginStatus_Success() {
        Login instance = new Login();

        instance.registerUser(
                "John",
                "Doe",
                "j_doe",
                "Password1!",
                "+27831234567"
        );

        String message = instance.returnLoginStatus(true);

        assertTrue(message.contains("Welcome John, Doe"));
    }

    @Test
    public void testReturnLoginStatus_Fail() {
        Login instance = new Login();

        String message = instance.returnLoginStatus(false);

        assertEquals("Username or password incorrect, please try again.", message);
    }
}