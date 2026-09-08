/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationtoapp;

/**
 *
 * @author Student
 */
public class LoginFeature {
  

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;

    // Constructor
    public LoginFeature(String firstName, String lastName, String username, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Check username: must contain an underscore and be no more than 5 characters long
    public boolean checkUsername() {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Check password requirements
    public boolean checkPassword() {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                hasCapitalLetter = true;
            } else if (Character.isDigit(character)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(character)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }

    // Check South African cellphone number (+27 followed by 9 digits)
    public boolean checkPhoneNumber() {
        return phoneNumber != null && phoneNumber.matches("^\\+27[0-9]{9}$");
    }

    // Register the user
    public String registerUser() {
        if (!checkUsername()) {
            return "Username is not correctly formatted; "
                    + "please ensure that your username contains "
                    + "an underscore and is no more than five "
                    + "characters in length.";
        }

        if (!checkPassword()) {
            return "Password is not correctly formatted; "
                    + "please ensure that the password contains "
                    + "at least eight characters, a capital letter, "
                    + "a number, and a special character.";
        }

        if (!checkPhoneNumber()) {
            return "Cell phone number is incorrectly formatted "
                    + "or does not contain international code.";
        }

        return "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell phone number successfully added.";
    }

    // Login user
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    // Return login status message
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }

   
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellPhoneNumber() {
        return phoneNumber;
    }
}