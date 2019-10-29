package com.testCase.userAdmin;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ConsoleApp {

	public static void main(String[] args) {
		ConsoleApp consoleApp = new ConsoleApp();
		consoleApp.startApp();
	}
	
	public void startApp() {
		int option = 100;
		
		Scanner in = new Scanner(System.in); 
		
		while(option > 0){
			option = 1;
			System.out.println("Enter the user of the DB: ");
			String userName = in.nextLine(); 
	        System.out.println("Enter the password for the user " + userName + ":"); 
	        String password = in.nextLine(); 
	        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(userName, password);
			
	        Session session = sessionFactory.openSession();
	        
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
					InsertBankUserConsole(session, in);
					break;
				case 2:
					SelectUser(session, in);
					break;
				case 3:
					HibernateUtils.readUserList(session);
					break;

				default:
					break;
				}
			}
			session.close();
		}
		
		System.out.println("Console end");
		in.close();
	}
	
	private void SelectUser(Session session, Scanner in) {
		int option = 1;
		int userId = 0;
		
		System.out.println("Enter the user id: ");
		try {
			userId = Integer.parseInt(in.nextLine());
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		
		BankUser user = HibernateUtils.selectUser(session, userId);
		
		
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
				InsertAccountConsole(session, in, user);
				break;
			case 2:
				SelectAccount(session, in, user);
				break;
			case 3:
				HibernateUtils.readAccountList(session, user);
				break;
			case 4:
				DeleteUser(session, in, user);
				break;
			case 5:
				EditUser(session, in, user);
				break;

			default:
				break;
			}
		}
	}

	private void EditUser(Session session, Scanner in, BankUser user) {
		int option = 1;
		int accountId = 0;
		
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
				EditUserFirstNameConsole(session, in, user);
				break;
			case 2:
				
			default:
				EditUserLastNameConsole(session, in, user);
				break;
			}
		}
		
	}

	private void EditUserFirstNameConsole(Session session, Scanner in, BankUser user) {
		System.out.print("Insert new First Name:\n");
		String firstName = in.nextLine();
		user.setFirst_name(firstName);
		HibernateUtils.EditUser(session, user);
	}
	
	private void EditUserLastNameConsole(Session session, Scanner in, BankUser user) {
		System.out.print("Insert new Last Name:\n");
		String lastName = in.nextLine();
		user.setLast_name(lastName);
		HibernateUtils.EditUser(session, user);
	}

	private void InsertBankUserConsole(Session session, Scanner in) {
		
		System.out.println("Insert first name: ");
		String firstName = in.nextLine();
		System.out.println("Insert last name: ");
		String lastName = in.nextLine();
		BankUser user = new BankUser();
		user.setFirst_name(firstName);
		user.setLast_name(lastName);
		HibernateUtils.insertUser(session, user);
	}
	
	private void InsertAccountConsole(Session session, Scanner in, BankUser user) {
		System.out.print("Insert IBAN (Except the 4 first digit):\n"
				+ Account.IBAN_CODE);
		String iban = in.nextLine();
		Account account = new Account();
		account.setIban(Account.IBAN_CODE + iban);
		HibernateUtils.insertAccount(session, user, account);
	}
	
	private void DeleteUser(Session session, Scanner in, BankUser user) {
		System.out.println("Are you sure you want to delete the user " + user.getFirst_name() + " " + user.getLast_name() + " and all the accounts? (y/n)");
		String delete = in.nextLine();
		if(delete.equals("y")) {
			HibernateUtils.DeleteUser(session, user);
		}
		
	}
	
	private void SelectAccount(Session session, Scanner in, BankUser user) {
		int option = 1;
		int accountId = 0;
		
		System.out.println("Enter the account id: ");
		try {
			accountId = Integer.parseInt(in.nextLine());
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		
		Account account = HibernateUtils.selectAccount(session, accountId);
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
				exit = DeleteAccountConsole(session, in, account);
				break;
			default:
				break;
			}
		}
	}

	private boolean DeleteAccountConsole(Session session, Scanner in, Account account) {
		System.out.println("Are you sure you want to delete the account " + account.getIban() + "? (y/n)");
		String delete = in.nextLine();
		if(delete.equals("y")) {
			HibernateUtils.DeleteAccount(session, account);
			return true;
		}
		return false;
	}
	
}
