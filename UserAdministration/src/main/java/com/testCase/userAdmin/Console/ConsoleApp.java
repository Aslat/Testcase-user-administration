package com.testCase.userAdmin.Console;

import java.util.List;
import java.util.Scanner;

import javax.transaction.SystemException;

import org.hibernate.HibernateException;

import com.testCase.userAdmin.HibernateUtils;
import com.testCase.userAdmin.ValidatorUtils;
import com.testCase.userAdmin.Entities.BankUser;

public class ConsoleApp {
	
	private boolean exit = false;
	private SelectUserConsole selectUserConsole = new SelectUserConsole();

	private Scanner in = new Scanner(System.in); 
	
	public static void main(String[] args) {
		ConsoleApp consoleApp = new ConsoleApp();
		consoleApp.startApp();
	}
	
	public void startApp() {
		int option = 100;
		
		while(option != 0){
			option = 1;
			exit = false;
			System.out.println("Enter the user of the DB: ");
			String userName = in.nextLine(); 
	        System.out.println("Enter the password for the user " + userName + ":"); 
	        String password = in.nextLine(); 
	        if(!HibernateUtils.openSession(userName, password)) {
				System.out.println("\nThe connection to the DB has failed\n");
				exit = true;
			}
	        
	        
			while(!exit) {
				System.out.print("\nLogged in as the administrator '" + userName + "':\n"
						+ "0 - Exit\n"
						+ "1 - Insert bank user\n"
						+ "2 - Select user\n"
						+ "3 - View users\n"
						+ "4 - Change DB user\n"
						+ "Select operation: ");
				try {
					option = Integer.parseInt(in.nextLine());
				} catch (NumberFormatException e) {
					option = 100;
				}
				
				switch (option) {
				case 0:
					exit = true;
					break;
				case 1:
					InsertBankUserConsole();
					break;
				case 2:
					selectUserConsole.SelectUser();
					break;
				case 3:
					readUserListConsole();
					break;
				case 4:
					exit = true;
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
		
		String firstName = null;
		String lastName = null;
		do {
			System.out.println("Insert first name: ");
			firstName = in.nextLine();
		}while(!ValidatorUtils.checkName(firstName));
		
		do {
			System.out.println("Insert last name: ");
			lastName = in.nextLine();
		}while(!ValidatorUtils.checkName(lastName));
		
		BankUser user = new BankUser();
		user.setFirst_name(firstName);
		user.setLast_name(lastName);
		HibernateUtils.insertUser(user);
	}
	
	private void readUserListConsole() {
		
		List<BankUser> users = HibernateUtils.readUserList();
		
		System.out.println("List of bank users");
        System.out.printf("%-30.30s  %-30.30s %-30.30s %n", "ID", "First Name", "Last Name");
        for (BankUser user : users) {
            System.out.printf("%-30.30s  %-30.30s  %-30.30s%n", user.getUser_id(), user.getFirst_name(), user.getLast_name());
        }
		System.out.println();
	}

	

}