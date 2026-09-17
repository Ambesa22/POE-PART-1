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
public class LoginFeatureTest {

    // Test data
    LoginFeature user = new LoginFeature(
            "John",
            "Smith",
            "j_s",
            "Password1!",
                "+27821234567"
    );

    // Test 1: Valid username
    @Test
    public void testCheckUsernameValid() {
        assertTrue(user.checkUsername());
    }

    // Test 2: Invalid username
    @Test
    public void testCheckUsernameInvalid() {

        LoginFeature invalidUser = new LoginFeature(
                "John",
                "Smith",
                "johnsmith",
                "Password1!",
                "+27821234567"
        );

        assertFalse(invalidUser.checkUsername());
    }

    // Test 3: Valid password
    @Test
    public void testCheckPasswordValid() {
        assertTrue(user.checkPassword());
    }

    // Test 4: Invalid password
    @Test
    public void testCheckPasswordInvalid() {

        LoginFeature invalidUser = new LoginFeature(
                "John",
                "Smith",
                "j_s",
                "password",
                "+27821234567"
        );

        assertFalse(invalidUser.checkPassword());
    }

    // Test 5: Valid phone number
    @Test
    public void testCheckPhoneNumberValid() {
        assertTrue(user.checkPhoneNumber());
    }

    // Test 6: Invalid phone number
    @Test
    public void testCheckPhoneNumberInvalid() {

        LoginFeature invalidUser = new LoginFeature(
                "John",
                "Smith",
                "j_s",
                "Password1!",
                "0821234567"
        );

        assertFalse(invalidUser.checkPhoneNumber());
    }

    // Test 7: Successful registration
    @Test
    public void testRegisterUserSuccessful() {

        String expected =
                "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell phone number successfully added.";

        assertEquals(expected, user.registerUser());
    }

    // Test 8: Failed registration because of username
    @Test
    public void testRegisterUserInvalidUsername() {

        LoginFeature invalidUser = new LoginFeature(
                "John",
                "Smith",
                "johnsmith",
                "Password1!",
                "+27821234567"
        );

        String expected =
                "Username is not correctly formatted; "
                + "please ensure that your username contains "
                + "an underscore and is no more than five "
                + "characters in length.";

        assertEquals(expected, invalidUser.registerUser());
    }

    // Test 9: Successful login
    @Test
    public void testLoginUserSuccessful() {
        assertTrue(
                user.loginUser("j_s", "Password1!")
        );
    }

    // Test 10: Failed login
    @Test
    public void testLoginUserFailed() {
        assertFalse(
                user.loginUser("wrong", "wrongpassword")
        );
    }

    // Test 11: Successful login status
    @Test
    public void testReturnLoginStatusSuccessful() {

        String expected =
                "Welcome John, Smith it is great to see you again.";

        assertEquals(
                expected,
                user.returnLoginStatus("j_s", "Password1!")
        );
    }

    // Test 12: Failed login status
    @Test
    public void testReturnLoginStatusFailed() {

        String expected =
                "Username or password incorrect, please try again.";

        assertEquals(
                expected,
                user.returnLoginStatus("wrong", "wrongpassword")
        );
    }

    // Test 13: First name getter
    @Test
    public void testGetFirstName() {
        assertEquals("John", user.getFirstName());
    }

    // Test 14: Last name getter
    @Test
    public void testGetLastName() {
        assertEquals("Smith", user.getLastName());
    }

    // Test 15: Username getter
    @Test
    public void testGetUsername() {
        assertEquals("j_s", user.getUsername());
    }

    // Test 16: Phone number getter
    @Test
    public void testGetCellPhoneNumber() {
        assertEquals("+27821234567", user.getCellPhoneNumber());
    }
}

