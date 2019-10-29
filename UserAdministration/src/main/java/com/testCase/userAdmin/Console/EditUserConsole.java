package com.testCase.userAdmin.Console;

import java.util.Scanner;

import com.testCase.userAdmin.HibernateUtils;
import com.testCase.userAdmin.ValidatorUtils;
import com.testCase.userAdmin.Entities.BankUser;

public class EditUserConsole {
	
	private boolean exit = false;
	private Scanner in = new Scanner(System.in);

	public void editUser(BankUser user) {
		int option = 1;
		
		while(!exit) {
			System.out.println("\nWhich attribute you want to edit for user " + user.getFirst_name() + " " + user.getLast_name() + "?:\n"
					+ "1 - First name\n"
					+ "2 - Last name\n"
					+ "3 - Back\n"
					+ "Select operation: ");
			try {
				option = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				option = 0;
			}
			
			switch (option) {
			case 1:
				editUserFirstNameConsole(user);
				break;
			case 2:
				editUserLastNameConsole(user);
				break;
			case 3:
				exit = true;
				
			default:
				break;
			}
		}
	}
	
	public void editUserFirstNameConsole(BankUser user) {
		String firstName = null;
		do {
			System.out.print("Insert new First Name:\n");
			firstName = in.nextLine();
		}while(!ValidatorUtils.checkName(firstName));
		
		user.setFirst_name(firstName);
		HibernateUtils.editUser(user);
	}
	
	public void editUserLastNameConsole(BankUser user) {
		String lastName = null;
		do {
			System.out.print("Insert new Last Name:\\n");
			lastName = in.nextLine();
		}while(!ValidatorUtils.checkName(lastName));
		
		user.setLast_name(lastName);
		HibernateUtils.editUser(user);
	}
	
}
