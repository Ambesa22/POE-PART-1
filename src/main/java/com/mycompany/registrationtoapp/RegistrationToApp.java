/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registrationtoapp;

import java.util.Scanner;


/**
 * 
 * 
 * 
 * 
 *
 * @author Student
 */

public class RegistrationToApp {

    public static void main(String[] args) {

        // Create Scanner for user input
        Scanner input = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("       WELCOME TO QUICKCHAT      ");
        System.out.println("=================================");

        // =================================
        // PART 1: USER REGISTRATION
        // =================================

        System.out.println("\n===== USER REGISTRATION =====");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine().trim();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine().trim();

        System.out.print(
                "Enter your username (must contain an underscore "
                + "and be no more than 5 characters): ");

        String username = input.nextLine().trim();

        System.out.print(
                "Enter your password (minimum 8 characters, "
                + "uppercase letter, number and special character): ");

        String password = input.nextLine();

        System.out.print(
                "Enter your South African cellphone number "
                + "(+27XXXXXXXXX or 0XXXXXXXXX): ");

        String phoneNumber = input.nextLine().trim();

        // Create LoginFeature object
        LoginFeature user = new LoginFeature(
                firstName,
                lastName,
                username,
                password,
                phoneNumber
        );

        // Check registration details
        System.out.println("\n===== REGISTRATION RESULTS =====");

        if (user.checkUsername()) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println(
                    "Username is incorrectly formatted. "
                    + "It must contain an underscore and "
                    + "be no more than 5 characters.");
        }

        if (user.checkPassword()) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println(
                    "Password is incorrectly formatted. "
                    + "It must contain at least 8 characters, "
                    + "an uppercase letter, a number and "
                    + "a special character.");
        }

        if (user.checkPhoneNumber()) {
            System.out.println(
                    "Cell phone number successfully captured.");
        } else {
            System.out.println(
                    "Cell phone number is incorrectly formatted.");
        }

        // Check whether all registration requirements are met
        if (!user.checkUsername()
                || !user.checkPassword()
                || !user.checkPhoneNumber()) {

            System.out.println(
                    "\nRegistration unsuccessful. "
                    + "Please restart the application "
                    + "and enter valid details.");

            input.close();
            return;
        }

        System.out.println("\n" + user.registerUser());

        // =================================
        // PART 1: USER LOGIN
        // =================================

        System.out.println("\n===== USER LOGIN =====");

        System.out.print("Enter your username: ");
        String loginUsername = input.nextLine().trim();

        System.out.print("Enter your password: ");
        String loginPassword = input.nextLine();

        // Check login details
        String loginStatus = user.returnLoginStatus(
                loginUsername,
                loginPassword
        );

        System.out.println("\n" + loginStatus);

        // =================================
        // PART 2 AND PART 3: QUICKCHAT
        // =================================

        if (user.loginUser(loginUsername, loginPassword)) {

            System.out.println("\nWelcome to QuickChat.");

            System.out.println(
                    "You can now send, store and manage messages.");

            // Create chat application using the same Scanner
            chatApplication application =
                    new chatApplication(input);

            // Start Part 2 and Part 3
            application.run();

        } else {

            System.out.println(
                    "\nLogin unsuccessful. "
                    + "Access to QuickChat has been denied.");
        }

        // Close Scanner after application finishes
        input.close();
    }
}

