package com.testCase.userAdmin;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.testCase.userAdmin.Entities.Account;
import com.testCase.userAdmin.Entities.BankUser;

public class HibernateUtils {	 
	
	private static SessionFactory sessionFactory = null;
	
    private static Session session = null;

 
    public static boolean openSession(String userName, String password) {
    	try {
    		Configuration cfg = new Configuration();
    		cfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
    		cfg.getProperties().setProperty("hibernate.connection.username",userName);
    		cfg.getProperties().setProperty("hibernate.connection.password",password);
    		
    		sessionFactory = cfg.buildSessionFactory();
    		session = sessionFactory.openSession();
		} catch (HibernateException e) {
			return false;
		}
    	return true;
    	
    }
    
    public static void closeSession() {
    	if(session != null && session.isOpen()) {
    		session.close();
    	}
    	if(sessionFactory != null && !sessionFactory.isClosed()) {
    		sessionFactory.close();
    	}
    	
    }
	
    public static boolean insertUser(BankUser user) {
    	try {
    		session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
		} catch (Exception e) {
			return false;
		}
    	return true;
    	
    }
    
    public static List<BankUser> readUserList() {
    	System.out.println();
    	
//    	Query q = session.createQuery("select _user from BankUser _user");
//        
//        List<BankUser> users = q.list();
        
        List<BankUser> users = session.createQuery("select _user from BankUser _user").list();
		
		return users;
    }

	public static BankUser selectUser(long userId) {    	
		BankUser user = (BankUser) session.get(BankUser.class, userId);
       
		return user;
	}
	
	public static boolean editUser(BankUser user) {  
		try {
			session.beginTransaction();
	        session.saveOrUpdate(user);
	        session.getTransaction().commit();
		} catch (Exception e) {
			return false;
		}
		return true;
        
	}
	
	public static boolean deleteUser(BankUser user) {
		try {
			session.beginTransaction();
			for(int i = 0; i < user.getAccounts().size(); i++) {
				session.delete(user.getAccounts().get(i));
			}
	        session.delete(user);
	        session.getTransaction().commit();
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}

	public static List<Account> readAccountList(BankUser user) {    	
//    	Query q = session.createQuery("select _account from Account _account where fk_user_id = '" + user.getUser_id() + "'");
//        
//    	List<Account> accounts = q.list();
    	
        List<Account> accounts = session.createQuery("select _account from Account _account where fk_user_id = '" + user.getUser_id() + "'").list();

    	
        return accounts;
	}

	public static boolean insertAccount(BankUser user, Account account) {
		account.setUser(user);
		user.getAccounts().add(account);
		
		try {
			session.beginTransaction();
			session.save(account);
	        session.save(user);
	        session.getTransaction().commit();
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}

	public static Account selectAccount(long accountId) {
		System.out.println();
		
		Account account = (Account) session.get(Account.class, accountId);
        
		return account;
	}

	public static boolean deleteAccount(Account account) {  
		try {
			session.beginTransaction();
	        session.delete(account);
	        session.getTransaction().commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
