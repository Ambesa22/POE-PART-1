/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registrationtoapp;

import java.util.Scanner;

/**
 *
 * @author Student
 */


public class RegistrationToApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println(
            "...... WELCOME TO THE REGISTRATION PAGE, LOGIN THE APP ......"
        );

        // Collect registration details
        System.out.print("Please enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Please enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Please enter your username: ");
        String username = input.nextLine();

        System.out.print("Please enter your password: ");
        String password = input.nextLine();

        System.out.print(
            "Please enter your cell phone number with the South African standard code: "
        );
        String phoneNumber = input.nextLine();

        // Instantiate the user object
        LoginFeature user = new LoginFeature(
            firstName,
            lastName,
            username,
            password,
            phoneNumber
        );

        // Registration process
        System.out.println("\n...... REGISTRATION PROCESS ......");

        String registrationStatus = user.registerUser();
        System.out.println(registrationStatus);

        // Check whether registration was successful
        if (user.checkUsername()
                && user.checkPassword()
                && user.checkPhoneNumber()) {

            System.out.println("\n...... LOGIN ......");

            // Collect login details
            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();

            // Display login status
            String loginStatus = user.returnLoginStatus(
                loginUsername,
                loginPassword
            );

            System.out.println(loginStatus);

            // this where the code for Part 1 and Part 2 connect
            if (user.loginUser(loginUsername, loginPassword)) {

                System.out.println("\n...... WELCOME TO CHAT APPLICATION ......");

                // Start the Part 2 messaging application
                chatApplication app = new chatApplication(input);
                app.run();

            } else {

                System.out.println(
                    "Access denied. chatApp was not opened."
                );
            }

        } else {

            System.out.println(
                "Registration unsuccessful. Please restart "
                + "and enter valid registration details."
            );
        }

        // Close the scanner after the application finishes
        input.close();
    }
}
