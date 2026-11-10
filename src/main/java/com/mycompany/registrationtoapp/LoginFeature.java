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
    // User details
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;

    // Constructor
    public LoginFeature(String firstName, String lastName,
                        String username, String password,
                        String phoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // PART 1: Check whether the username is valid.
    // Username must contain an underscore and be no longer than 5 characters.
    public boolean checkUsername() {

        if (username != null
                && username.contains("_")
                && username.length() <= 5) {

            System.out.println("Username successfully captured.");
            return true;

        } else {

            System.out.println(
                "Username is not correctly formatted. "
                + "Please ensure that your username contains "
                + "an underscore and is no more than 5 characters long."
            );

            return false;
        }
    }

    // PART 1: Check whether the password is valid.
    // Password must contain at least 8 characters,
    // an uppercase letter, a number and a special character.
    public boolean checkPassword() {

        if (password != null
                && password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*")) {

            System.out.println("Password successfully captured.");
            return true;

        } else {

            System.out.println(
                "Password is not correctly formatted. "
                + "Please ensure that the password contains "
                + "at least 8 characters, a capital letter, "
                + "a number and a special character."
            );

            return false;
        }
    }

    // PART 1: Validate a South African cellphone number.
    // Accepts either +27 followed by 9 digits,
    // or 0 followed by 9 digits.
    public boolean checkPhoneNumber() {

        if (phoneNumber != null
                && (phoneNumber.matches("^\\\\+27[0-9]{9}$")
                || phoneNumber.matches("^0[0-9]{9}$"))) {

            System.out.println("Cellphone number successfully captured.");
            return true;

        } else {

            System.out.println(
                "Cellphone number is incorrectly formatted. "
                + "Please enter a South African number using "
                + "+27 followed by 9 digits or 0 followed by 9 digits."
            );

            return false;
        }
    }

    // PART 1: Register the user.
    public String registerUser() {

        if (checkUsername() && checkPassword() && checkPhoneNumber()) {

            return "Registration successful.";

        } else {

            return "Registration unsuccessful. "
                    + "Please check your details and try again.";
        }
    }

    // PART 1: Verify the login details.
    public boolean loginUser(String enteredUsername,
                             String enteredPassword) {

        return username != null
                && password != null
                && username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }

    // PART 1: Return the login status.
    public String returnLoginStatus(String enteredUsername,
                                    String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {

            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";

        } else {

            return "Username or password incorrect, "
                    + "please try again.";
        }
    }

    // PART 2 AND PART 3: Getters for user information.
    // These allow other classes to access the user's details.

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // PART 2 AND PART 3: Get the full name of the registered user.
    public String getFullName() {
        return firstName + " " + lastName;
    } 
}