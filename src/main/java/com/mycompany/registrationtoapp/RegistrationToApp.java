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
      

        Scanner input = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("       Welcome to QuickChat");
        System.out.println("==================================");

        // PART 1: USER REGISTRATION

        System.out.println("\nPlease register your account.");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        System.out.print("Enter your South African cellphone number: ");
        String phoneNumber = input.nextLine();

        // Create a user object
        LoginFeature user = new LoginFeature(
                firstName,
                lastName,
                username,
                password,
                phoneNumber
        );

        // Validate each registration detail ONCE
        boolean usernameValid = user.checkUsername();
        boolean passwordValid = user.checkPassword();
        boolean phoneValid = user.checkPhoneNumber();

        // Display validation results
        System.out.println("\n========== REGISTRATION RESULTS ==========");

        if (usernameValid) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println(
                    "Username is not correctly formatted. "
                    + "Please ensure that it contains an underscore "
                    + "and is no more than five characters long."
            );
        }

        if (passwordValid) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println(
                    "Password is not correctly formatted. "
                    + "It must contain at least eight characters, "
                    + "a capital letter, a number and a special character."
            );
        }

        if (phoneValid) {
            System.out.println("Cellphone number successfully captured.");
        } else {
            System.out.println(
                    "Cellphone number is incorrectly formatted. "
                    + "Please enter a valid South African number."
            );
        }

        // Check whether all registration details are valid
        if (usernameValid && passwordValid && phoneValid) {

            System.out.println("\n" + user.registerUser());

            // PART 1: USER LOGIN

            System.out.println("\n========== LOGIN ==========");

            System.out.print("Enter your username: ");
            String enteredUsername = input.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = input.nextLine();

            // Check login details
            if (user.loginUser(enteredUsername, enteredPassword)) {

                System.out.println(
                        user.returnLoginStatus(
                                enteredUsername,
                                enteredPassword
                        )
                );

                // PARTS 2 AND 3: OPEN QUICKCHAT
                System.out.println("\nWelcome to QuickChat.");

                // Pass the registered user's full name
                chatApplication application =
                        new chatApplication(input, user.getFullName());

                // Start the messaging application
                application.run();

            } else {

                System.out.println(
                        user.returnLoginStatus(
                                enteredUsername,
                                enteredPassword
                        )
                );

                System.out.println(
                        "Login unsuccessful. Please try again."
                );
            }

        } else {

            System.out.println(
                    "\nRegistration unsuccessful. "
                    + "Please correct your details and register again."
            );
        }

        input.close();
        System.out.println("\nThank you for using QuickChat.");
    }
}


      
      