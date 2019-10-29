package com.testCase.userAdmin.Console;

import java.util.Scanner;

import com.testCase.userAdmin.HibernateUtils;
import com.testCase.userAdmin.Entities.Account;
import com.testCase.userAdmin.Entities.BankUser;

public class SelectAccountConsole {

	private Scanner in = new Scanner(System.in);
	
	public void SelectAccount(BankUser user) {
		int option = 1;
		long accountId = 0;
		
		System.out.println("Enter the account id: ");
		try {
			accountId = Long.parseLong(in.nextLine());
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		
		Account account = HibernateUtils.selectAccount(accountId);
		boolean exit = false;
		
		while(option < 2 && !exit) {
			System.out.println("Operations for account " + account.getIban() + ":\n"
					+ "1 - Delete account\n"
					+ "2 - Back\n"
					+ "Select operation: ");
			try {
				option = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
			
			switch (option) {
			case 1:
				exit = deleteAccountConsole(account);
				break;
			default:
				break;
			}
		}
	}
	
	public boolean deleteAccountConsole(Account account) {
		System.out.println("Are you sure you want to delete the account " + account.getIban() + "? (y/n)");
		String delete = in.nextLine();
		if(delete.equals("y")) {
			HibernateUtils.deleteAccount(account);
			return true;
		}
		return false;
	}
	
}
