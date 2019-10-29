package com.testCase.userAdmin.Console;

import java.util.Scanner;

import com.testCase.userAdmin.HibernateUtils;
import com.testCase.userAdmin.ValidatorUtils;
import com.testCase.userAdmin.Entities.Account;
import com.testCase.userAdmin.Entities.BankUser;

public class SelectUserConsole {

	boolean exit = false;
	private SelectAccountConsole selectAccountConsole = new SelectAccountConsole();
	private EditUserConsole editUserConsole = new EditUserConsole();
	private Scanner in = new Scanner(System.in);
	
	public void SelectUser() {
		int option = 0;
		exit = false;
		long userId = 0;
		
		System.out.println("Enter the user id: ");
		try {
			userId = Long.parseLong(in.nextLine());
		} catch (NumberFormatException e) {
			userId = -1;
		}
		
		BankUser user = HibernateUtils.selectUser(userId);
		
		if(user == null) {
			System.out.println("\nEl usuario introducido es incorrecto\n");
			exit = true;
		}
		
		
		while(!exit) {
			System.out.println("\nOperations for user " + user.getFirst_name() + ":\n"
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
				option = 0;
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
				exit = deleteUser(user);
				break;
			case 5:
				editUserConsole.editUser(user);
				break;
			case 6:
				exit = true;
				break;
			default:
				break;
			}
		}
	}
	
	public void insertAccountConsole(BankUser user) {
		String iban = null;
		do {
			System.out.print("Insert IBAN (Except the 4 first digit):\n"
					+ Account.IBAN_CODE);
			iban = in.nextLine();
		}while(!ValidatorUtils.checkIban(iban));
		
		Account account = new Account();
		account.setIban(Account.IBAN_CODE + iban);
		HibernateUtils.insertAccount(user, account);
	}
	
	public boolean deleteUser(BankUser user) {
		System.out.println("Are you sure you want to delete the user " + user.getFirst_name() + " " + user.getLast_name() + " and all the accounts? (y/n)");
		String delete = in.nextLine();
		if(delete.equals("y")) {
			HibernateUtils.deleteUser(user);
			return true;
		}
		return false;
		
	}
}
