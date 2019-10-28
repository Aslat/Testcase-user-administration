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
				System.out.print("Operations for user " + userName + ":\n"
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
			System.out.println("Select operation for user " + user.getFirst_name() + ":\n"
					+ "1 - Insert account\n"
					+ "2 - Select account\n"
					+ "3 - View accounts\n"
					+ "4 - Delete user\n"
					+ "5 - Edit user\n"
					+ "6 - Back");
			try {
				option = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
			
			switch (option) {
			case 1:
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
	
				break;

			default:
				break;
			}
		}
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
	
}
