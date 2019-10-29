package com.testCase.userAdmin.Console;

import java.util.Scanner;

import com.testCase.userAdmin.HibernateUtils;
import com.testCase.userAdmin.Entities.Account;
import com.testCase.userAdmin.Entities.BankUser;

public class SelectUserConsole {

	private SelectAccountConsole selectAccountConsole = new SelectAccountConsole();
	private Scanner in = new Scanner(System.in);
	
	public void SelectUser() {
		int option = 1;
		long userId = 0;
		
		System.out.println("Enter the user id: ");
		try {
			userId = Long.parseLong(in.nextLine());
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		
		BankUser user = HibernateUtils.selectUser(userId);
		
		
		while(option < 6 ) {
			System.out.println("Operations for user " + user.getFirst_name() + ":\n"
					+ "1 - Insert account\n"
					+ "2 - Select account\n"
					+ "3 - View accounts\n"
					+ "4 - Delete user\n"
					+ "5 - Edit user\n"
					+ "6 - Back\n"
					+ "Select operation: ");
			try {
				option = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
			
			switch (option) {
			case 1:
				insertAccountConsole(user);
				break;
			case 2:
				selectAccountConsole.SelectAccount(user);
				break;
			case 3:
				HibernateUtils.readAccountList(user);
				break;
			case 4:
				deleteUser(user);
				break;
			case 5:
				editUser(user);
				break;

			default:
				break;
			}
		}
	}
	
	public void editUser(BankUser user) {
		int option = 1;
		
		while(option < 2 ) {
			System.out.println("Which attribute you want to edit for user " + user.getFirst_name() + " " + user.getLast_name() + "?:\n"
					+ "1 - First name\n"
					+ "2 - Last name\n"
					+ "3 - Back\n"
					+ "Select operation: ");
			try {
				option = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
			
			switch (option) {
			case 1:
				editUserFirstNameConsole(user);
				break;
			case 2:
				
			default:
				editUserLastNameConsole(user);
				break;
			}
		}
		
	}
	
	public void insertAccountConsole(BankUser user) {
		System.out.print("Insert IBAN (Except the 4 first digit):\n"
				+ Account.IBAN_CODE);
		String iban = in.nextLine();
		Account account = new Account();
		account.setIban(Account.IBAN_CODE + iban);
		HibernateUtils.insertAccount(user, account);
	}
	
	public void deleteUser(BankUser user) {
		System.out.println("Are you sure you want to delete the user " + user.getFirst_name() + " " + user.getLast_name() + " and all the accounts? (y/n)");
		String delete = in.nextLine();
		if(delete.equals("y")) {
			HibernateUtils.deleteUser(user);
		}
		
	}

	public void editUserFirstNameConsole(BankUser user) {
		System.out.print("Insert new First Name:\n");
		String firstName = in.nextLine();
		user.setFirst_name(firstName);
		HibernateUtils.editUser(user);
	}
	
	public void editUserLastNameConsole(BankUser user) {
		System.out.print("Insert new Last Name:\n");
		String lastName = in.nextLine();
		user.setLast_name(lastName);
		HibernateUtils.editUser(user);
	}
	
}
