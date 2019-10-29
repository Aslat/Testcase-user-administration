package com.testCase.userAdmin;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernateUtils {	 

 
    public static SessionFactory buildSessionFactory(String userName, String password) {
    	Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
		cfg.getProperties().setProperty("hibernate.connection.username",userName);
		cfg.getProperties().setProperty("hibernate.connection.password",password);
		return cfg.buildSessionFactory();
    }
	
    public static void insertUser(Session session, BankUser user) {
    	session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }
    
    public static void readUserList(Session session) {
    	System.out.println();
    	
    	Query q = session.createQuery("select _user from BankUser _user");
        
        List<BankUser> users = q.list();
         
        System.out.println("List of bank users");
        System.out.printf("%-30.30s  %-30.30s %-30.30s %n", "ID", "First Name", "Last Name");
        for (BankUser user : users) {
            System.out.printf("%-30.30s  %-30.30s  %-30.30s%n", user.getUser_id(), user.getFirst_name(), user.getLast_name());
        }
		System.out.println();
    }

	public static BankUser selectUser(Session session, int userId) {
		System.out.println();
    	
    	Query q = session.createQuery("select _user from BankUser _user where user_id = '" + userId + "'");
        
        List<BankUser> users = q.list();
         
        BankUser user = users.get(0);
        
		return user;
	}
	
	public static void EditUser(Session session, BankUser user) {         
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
	}
	
	public static void DeleteUser(Session session, BankUser user) {
		session.beginTransaction();
		for(int i = 0; i < user.getAccounts().size(); i++) {
			session.delete(user.getAccounts().get(i));
		}
        session.delete(user);
        session.getTransaction().commit();
	}

	public static void readAccountList(Session session, BankUser user) {
		System.out.println();
    	
    	Query q = session.createQuery("select _account from Account _account where fk_user_id = '" + user.getUser_id() + "'");
        
        List<Account> accounts = q.list();
         
        System.out.println("List of Account of user " + user.getFirst_name() + " " + user.getLast_name());
        System.out.printf("%-30.30s  %-30.30s %n", "ID", "IBAN");
        for (Account account : accounts) {
            System.out.printf("%-30.30s  %-30.30s %n", account.getAccount_id(), account.getIban());
        }
		System.out.println();
	}

	public static void insertAccount(Session session, BankUser user, Account account) {
		account.setUser(user);
		user.getAccounts().add(account);
		
		session.beginTransaction();
		session.save(account);
        session.save(user);
        session.getTransaction().commit();
	}

	public static Account selectAccount(Session session, int accountId) {
		System.out.println();
    	
    	Query q = session.createQuery("select _account from Account _account where account_id = '" + accountId + "'");
        
        List<Account> accounts = q.list();
         
        Account account = accounts.get(0);
        
		return account;
	}

	public static void DeleteAccount(Session session, Account account) {         
        session.beginTransaction();
        session.delete(account);
        session.getTransaction().commit();
	}
}
