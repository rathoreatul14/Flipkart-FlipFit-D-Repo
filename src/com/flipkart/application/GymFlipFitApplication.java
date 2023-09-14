package com.flipkart.application;

import java.time.LocalDate;
import java.util.Scanner;

import com.flipkart.bean.User;

public class GymFlipFitApplication {

	public static void main(String[] args) {
		System.out.println("\nWelcome to FlipFit Gym Application");
		Scanner in = new Scanner(System.in);
		int choice = 1;
		openLoginMenu(in);
		while (choice != 4) {

			System.out.println("\nMenu:-");
			System.out.println("\t1. Login\n" + "\t2. Admin Registration\n" + "\t3. Customer Registration\n" + "\t4. GymOwner Registration" + "\t5. Exit\n");

			System.out.print("$ Enter your choice: ");

			try {
				choice = in.nextInt();
				in.nextLine(); // Consume the newline character after reading the integer

				switch (choice) {
				case 1:
					openLoginMenu(in);
					break;
				case 2:
					GymFlipFitAdminMenu admin = new GymFlipFitAdminMenu();
					admin.run(in);
					break;
				case 3:
					GymFlipFitCustomerMenu customer = new GymFlipFitCustomerMenu();
					customer.run(in);
					break;
				case 4:
					GymFlipFitGymOwnerMenu gymOwner = new GymFlipFitGymOwnerMenu();
					gymOwner.run(in);
					break;
				case 5:
//					System.exit(0);
					break;

				default:
					System.out.println("Invalid Credentials");
				}
			} catch (Exception e) {
				System.out.println("Error");
			}

		}
		System.out.println("Thankyou for visiting");
		in.close();
	}

	public static void openLoginMenu(Scanner in) {
		// Login check
		System.out.println("\nEnter your login credentials:-");
		
		System.out.print("$ UserId: ");
		String userName = in.next();
		
		System.out.print("$ Password: ");
		String password = in.next();
		
		// authenticate
		// hard coded right now
		int role = 1;
		LocalDate localDate = LocalDate.now();
		System.out.println(localDate.getDayOfMonth() + "/" + localDate.getMonth() + "/" + localDate.getYear());
		System.out.println("Hello!! " + userName + "\nWelcome to GMS");
		
		switch (role) {
			case 1: 
				GymFlipFitAdminMenu admin = new GymFlipFitAdminMenu();
				admin.adminActionPage(in);
				break;
				
			case 2: 
				GymFlipFitCustomerMenu customer = new GymFlipFitCustomerMenu();
//				customer.gymOwnerActionPage(in, user);
				break;
				
			case 3: 
				GymFlipFitGymOwnerMenu owner = new GymFlipFitGymOwnerMenu();
//				owner.customerActionPage(in, user);
				break;
				
			default:
				System.out.println("\nIncorrect choice!!! Please try again!!!");
				break;
		}
		
	}
}
