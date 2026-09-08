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
  {
        
        System.out.println("...... WELCOME TO THE REGISTRATION PAGE, LOGIN THE APP ......");

        System.out.print("Please enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Please enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Please enter your username: ");
        String username = input.nextLine();

        System.out.print("Please enter your password: ");
        String password = input.nextLine();

        System.out.print("Please enter your cell phone number with the south african standaed code: ");
        String phoneNumber = input.nextLine();

        // Instantiate the user object
        LoginFeature user = new LoginFeature(firstName, lastName, username, password, phoneNumber);

        // Register user
        System.out.println("\n...... REGISTRATION PROCESS ......");
        System.out.println(user.registerUser()); // Updated method call name

        // Only continue to login if registration was successful
        if (user.checkUsername() && user.checkPassword() && user.checkPhoneNumber()) {

            System.out.println("\n...... LOGIN ......");

            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();

            System.out.println(
                user.returnLoginStatus(loginUsername, loginPassword)
            );
        }

        input.close();
    }
    }
}
    