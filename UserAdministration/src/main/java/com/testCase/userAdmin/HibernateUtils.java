package com.testCase.userAdmin;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.testCase.userAdmin.Entities.Account;
import com.testCase.userAdmin.Entities.BankUser;

public class HibernateUtils {	 
	
	private static SessionFactory sessionFactory = null;
	
    private static Session session = null;

 
    public static void openSession(String userName, String password) {
    	Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
		cfg.getProperties().setProperty("hibernate.connection.username",userName);
		cfg.getProperties().setProperty("hibernate.connection.password",password);
		
		sessionFactory = cfg.buildSessionFactory();
		
        session = sessionFactory.openSession();
    }
    
    public static void closeSession() {
    	session.close();
    	sessionFactory.close();
    }
	
    public static void insertUser(BankUser user) {
    	session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }
    
    public static void readUserList() {
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

	public static BankUser selectUser(long userId) {
		System.out.println();
    	
		BankUser user = (BankUser) session.get(BankUser.class, userId);
        
		return user;
	}
	
	public static void editUser(BankUser user) {         
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
	}
	
	public static void deleteUser(BankUser user) {
		session.beginTransaction();
		for(int i = 0; i < user.getAccounts().size(); i++) {
			session.delete(user.getAccounts().get(i));
		}
        session.delete(user);
        session.getTransaction().commit();
	}

	public static void readAccountList(BankUser user) {
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

	public static void insertAccount(BankUser user, Account account) {
		account.setUser(user);
		user.getAccounts().add(account);
		
		session.beginTransaction();
		session.save(account);
        session.save(user);
        session.getTransaction().commit();
	}

	public static Account selectAccount(long accountId) {
		System.out.println();
		
		Account account = (Account) session.get(Account.class, accountId);
        
		return account;
	}

	public static void deleteAccount(Account account) {         
        session.beginTransaction();
        session.delete(account);
        session.getTransaction().commit();
	}
}
