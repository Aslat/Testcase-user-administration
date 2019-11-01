package com.testCase.userAdmin.Console;

import java.util.Scanner;

import com.testCase.userAdmin.HibernateUtils;
import com.testCase.userAdmin.Entities.Account;
import com.testCase.userAdmin.Entities.BankUser;

public class SelectAccountConsole {

	private Scanner in = new Scanner(System.in);
	boolean exit = false;
	
	public void SelectAccount(BankUser user) {
		int option = 1;
		exit = false;
		long accountId = 0;
		
		System.out.println("Enter the account id: ");
		try {
			accountId = Long.parseLong(in.nextLine());
		} catch (NumberFormatException e) {
			accountId = -1;
		}
		
		Account account = HibernateUtils.selectAccount(accountId);
		
		if(account == null) {
			System.out.println("\nLa cuenta introducida es incorrecta\n");
			exit = true;
		}
		
		while(!exit) {
			System.out.println("\nOperations for account " + account.getIban() + ":\n"
					+ "1 - Delete account\n"
					+ "2 - Back\n"
					+ "Select operation: ");
			try {
				option = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				option = 0;
			}
			
			switch (option) {
			case 1:
				exit = deleteAccountConsole(account);
				break;
			case 2:
				exit = true;
			default:
				break;
			}
		}
	}
	
	public boolean deleteAccountConsole(Account account) {
		System.out.println("Are you sure you want to delete the account " + account.getIban() + "? (y/n)");
		String delete = in.nextLine();
		if(delete.equals("y")) {
			if(HibernateUtils.deleteAccount(account)) {
				System.out.println("You don't have permission to delete this account");
			}
			return true;
		}
		return false;
	}
	
}
