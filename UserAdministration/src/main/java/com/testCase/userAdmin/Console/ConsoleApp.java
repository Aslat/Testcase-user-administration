package com.testCase.userAdmin.Console;

import java.util.Scanner;

import com.testCase.userAdmin.HibernateUtils;
import com.testCase.userAdmin.Entities.BankUser;

public class ConsoleApp {
	
	private SelectUserConsole selectUserConsole = new SelectUserConsole();

	private Scanner in = new Scanner(System.in); 
	
	public static void main(String[] args) {
		ConsoleApp consoleApp = new ConsoleApp();
		consoleApp.startApp();
	}
	
	public void startApp() {
		int option = 100;
		
		while(option > 0){
			option = 1;
			System.out.println("Enter the user of the DB: ");
			String userName = in.nextLine(); 
	        System.out.println("Enter the password for the user " + userName + ":"); 
	        String password = in.nextLine(); 
	        HibernateUtils.openSession(userName, password);
	        
			while(option > 0 && option < 4 ) {
				System.out.print("Logged in as the administrator '" + userName + "':\n"
						+ "0 - Exit\n"
						+ "1 - Insert bank user\n"
						+ "2 - Select user\n"
						+ "3 - View users\n"
						+ "4 - Change DB user\n"
						+ "Select operation: ");
				try {
					option = Integer.parseInt(in.nextLine());
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
				
				switch (option) {
				case 1:
					InsertBankUserConsole();
					break;
				case 2:
					selectUserConsole.SelectUser();
					break;
				case 3:
					HibernateUtils.readUserList();
					break;

				default:
					break;
				}
			}
			HibernateUtils.closeSession();
		}
		
		System.out.println("Console end");
		in.close();
	}

	private void InsertBankUserConsole() {
		System.out.println("Insert first name: ");
		String firstName = in.nextLine();
		System.out.println("Insert last name: ");
		String lastName = in.nextLine();
		BankUser user = new BankUser();
		user.setFirst_name(firstName);
		user.setLast_name(lastName);
		HibernateUtils.insertUser(user);
	}

}