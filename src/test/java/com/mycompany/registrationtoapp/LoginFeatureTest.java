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

    // Create a valid user for testing.
    private LoginFeature createValidUser() {

        return new LoginFeature(
                "Mike",
                "Smith",
                "m_123",
                "Password1!",
                "+27821234567"
        );
    }

    // ==========================================
    // PART 1: USERNAME VALIDATION TESTS
    // ==========================================

    @Test
    public void testUsernameCorrectlyFormatted() {

        LoginFeature user = createValidUser();

        assertTrue(user.checkUsername());
    }

    @Test
    public void testUsernameWithoutUnderscore() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "mike1",
                "Password1!",
                "+27821234567"
        );

        assertFalse(user.checkUsername());
    }

    @Test
    public void testUsernameLongerThanFiveCharacters() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "mike_123",
                "Password1!",
                "+27821234567"
        );

        assertFalse(user.checkUsername());
    }

    @Test
    public void testUsernameWithUnderscoreAndFiveCharacters() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "m_123",
                "Password1!",
                "+27821234567"
        );

        assertTrue(user.checkUsername());
    }

    // ==========================================
    // PART 1: PASSWORD VALIDATION TESTS
    // ==========================================

    @Test
    public void testPasswordCorrectlyFormatted() {

        LoginFeature user = createValidUser();

        assertTrue(user.checkPassword());
    }

    @Test
    public void testPasswordWithoutCapitalLetter() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "m_123",
                "password1!",
                "+27821234567"
        );

        assertFalse(user.checkPassword());
    }

    @Test
    public void testPasswordWithoutNumber() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "m_123",
                "Password!",
                "+27821234567"
        );

        assertFalse(user.checkPassword());
    }

    @Test
    public void testPasswordWithoutSpecialCharacter() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "m_123",
                "Password12",
                "+27821234567"
        );

        assertFalse(user.checkPassword());
    }

    @Test
    public void testPasswordLessThanEightCharacters() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "m_123",
                "Pass1!",
                "+27821234567"
        );

        assertFalse(user.checkPassword());
    }

    // ==========================================
    // PART 1: CELLPHONE NUMBER VALIDATION TESTS
    // ==========================================

    @Test
    public void testInternationalCellphoneNumber() {

        LoginFeature user = createValidUser();

        assertTrue(user.checkPhoneNumber());
    }

    @Test
    public void testLocalSouthAfricanCellphoneNumber() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "m_123",
                "Password1!",
                "0821234567"
        );

        assertTrue(user.checkPhoneNumber());
    }

    @Test
    public void testInvalidCellphoneNumber() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "m_123",
                "Password1!",
                "08212345"
        );

        assertFalse(user.checkPhoneNumber());
    }

    // ==========================================
    // PART 1: REGISTRATION TESTS
    // ==========================================

    @Test
    public void testSuccessfulRegistration() {

        LoginFeature user = createValidUser();

        assertEquals(
                "Registration successful.",
                user.registerUser()
        );
    }

    @Test
    public void testUnsuccessfulRegistration() {

        LoginFeature user = new LoginFeature(
                "Mike",
                "Smith",
                "mike123",
                "password",
                "08212345"
        );

        assertEquals(
                "Registration unsuccessful. "
                + "Please check your details and try again.",
                user.registerUser()
        );
    }

    // ==========================================
    // PART 1: LOGIN TESTS
    // ==========================================

    @Test
    public void testSuccessfulLogin() {

        LoginFeature user = createValidUser();

        assertTrue(
                user.loginUser("m_123", "Password1!")
        );
    }

    @Test
    public void testUnsuccessfulLoginWrongUsername() {

        LoginFeature user = createValidUser();

        assertFalse(
                user.loginUser("wrong", "Password1!")
        );
    }

    @Test
    public void testUnsuccessfulLoginWrongPassword() {

        LoginFeature user = createValidUser();

        assertFalse(
                user.loginUser("m_123", "WrongPassword1!")
        );
    }

    @Test
    public void testLoginStatusSuccessful() {

        LoginFeature user = createValidUser();

        assertEquals(
                "Welcome Mike, Smith it is great to see you again.",
                user.returnLoginStatus("m_123", "Password1!")
        );
    }

    @Test
    public void testLoginStatusUnsuccessful() {

        LoginFeature user = createValidUser();

        assertEquals(
                "Username or password incorrect, please try again.",
                user.returnLoginStatus("m_123", "wrong")
        );
    }

    // ==========================================
    // PART 2 AND PART 3: USER INFORMATION TESTS
    // ==========================================

    @Test
    public void testGetFirstName() {

        LoginFeature user = createValidUser();

        assertEquals("Mike", user.getFirstName());
    }

    @Test
    public void testGetLastName() {

        LoginFeature user = createValidUser();

        assertEquals("Smith", user.getLastName());
    }

    @Test
    public void testGetUsername() {

        LoginFeature user = createValidUser();

        assertEquals("m_123", user.getUsername());
    }

    @Test
    public void testGetPhoneNumber() {

        LoginFeature user = createValidUser();

        assertEquals("+27821234567", user.getPhoneNumber());
    }

    @Test
    public void testGetFullName() {

        LoginFeature user = createValidUser();

        assertEquals("Mike Smith", user.getFullName());
    }
}   