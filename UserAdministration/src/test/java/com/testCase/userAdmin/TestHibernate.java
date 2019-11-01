package com.testCase.userAdmin;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.testCase.userAdmin.Entities.Account;
import com.testCase.userAdmin.Entities.BankUser;

public class TestHibernate {

	@Before
    public void init() {
		HibernateUtils.openSession("postgres", "admin");
		
        List<BankUser> users = HibernateUtils.readUserList();
        for(int i = 0; i < users.size(); i++) {
        	HibernateUtils.deleteUser(users.get(i));
        }
        
        HibernateUtils.closeSession();
    }
	
	@Test
	public void testInsertUser() {		
		HibernateUtils.openSession("bankadmin1", "admin");
		
		BankUser user = new BankUser();
		user.setFirst_name("John");
		user.setLast_name("Doe");
		HibernateUtils.insertUser(user);		
		List<BankUser> users = HibernateUtils.readUserList();
		assertTrue("Testing the INSERT for BankUser", users.contains(user));
		
		HibernateUtils.closeSession();
	}
	
	@Test
	public void testUpdateUser() {		
		HibernateUtils.openSession("bankadmin1", "admin");
		
		BankUser user = new BankUser();
		user.setFirst_name("John");
		user.setLast_name("Doe");
		HibernateUtils.insertUser(user);		
		
		user.setFirst_name("Johny");
		HibernateUtils.editUser(user);
		assertEquals("Testing the UPDATE for BankUser", "Johny", HibernateUtils.selectUser(user.getUser_id()).getFirst_name());
				
		HibernateUtils.closeSession();
	}
	
	@Test
	public void testDeleteUser() {		
		HibernateUtils.openSession("bankadmin1", "admin");
		
		BankUser user = new BankUser();
		user.setFirst_name("John");
		user.setLast_name("Doe");
		HibernateUtils.insertUser(user);		
		
		Account account1 = new Account();
		account1.setIban(Account.IBAN_CODE + "234567890123456782");
		HibernateUtils.insertAccount(user, account1);
		
		Account account2 = new Account();
		account2.setIban(Account.IBAN_CODE + "312345678901234567");
		HibernateUtils.insertAccount(user, account2);
				
		HibernateUtils.deleteUser(user);
		List<BankUser> users = HibernateUtils.readUserList();
		users = HibernateUtils.readUserList();
		assertFalse("Testing the DELETE for BankUser", users.contains(user));
		
		HibernateUtils.closeSession();
	}
	
	@Test
	public void testInsertAccount() {		
		HibernateUtils.openSession("bankadmin1", "admin");
		
		BankUser user = new BankUser();
		user.setFirst_name("John");
		user.setLast_name("Doe");
		HibernateUtils.insertUser(user);
		
		Account account = new Account();
		account.setIban(Account.IBAN_CODE + "123456789012345678");
		HibernateUtils.insertAccount(user, account);
		List<Account> accounts = HibernateUtils.readAccountList(user);
		assertTrue("Testing the INSERT for Account", accounts.contains(account));
		
		Account account2 = new Account();
		account2.setIban(Account.IBAN_CODE + "123456789012345678");
		HibernateUtils.insertAccount(user, account2);
		accounts = HibernateUtils.readAccountList(user);
		assertFalse("Testing the INSERT for a duplidated Account", accounts.contains(account2));
				
		HibernateUtils.closeSession();
	}
	
	@Test
	public void testDeleteAccount() {		
		HibernateUtils.openSession("bankadmin1", "admin");
		
		BankUser user = new BankUser();
		user.setFirst_name("John");
		user.setLast_name("Doe");
		HibernateUtils.insertUser(user);
		
		Account account = new Account();
		account.setIban(Account.IBAN_CODE + "123456789012345678");
		HibernateUtils.insertAccount(user, account);
		
		HibernateUtils.deleteAccount(account);
		List<Account> accounts = HibernateUtils.readAccountList(user);
		assertFalse("Testing the DELETE for Account", accounts.contains(account));
						
		HibernateUtils.closeSession();
	}
	
	@Test
	public void testAdminPermissionsForUser() {
		HibernateUtils.openSession("bankadmin1", "admin");
		
		BankUser user1 = new BankUser();
		user1.setFirst_name("John");
		user1.setLast_name("Doe");
		HibernateUtils.insertUser(user1);
		
		HibernateUtils.closeSession();
		
		HibernateUtils.openSession("bankadmin2", "admin");
		
		assertFalse("Testing Permission to READ a user", HibernateUtils.readUserList().isEmpty());
		
		user1.setFirst_name("Johnny");
		HibernateUtils.editUser(user1);
		assertNotEquals("Testing Permission to UPDATE a user", "Johny", HibernateUtils.selectUser(user1.getUser_id()).getFirst_name());
		
		HibernateUtils.deleteUser(user1);
		List<BankUser> users = HibernateUtils.readUserList();
		users = HibernateUtils.readUserList();
		assertFalse("Testing Permission to DELETE a BankUser", users.contains(user1));
		
		HibernateUtils.closeSession();
	}
	
	@Test
	public void testAdminPermissionsForAccount() {
		HibernateUtils.openSession("bankadmin1", "admin");
		
		BankUser user1 = new BankUser();
		user1.setFirst_name("John");
		user1.setLast_name("Doe");
		HibernateUtils.insertUser(user1);
		
		Account account = new Account();
		account.setIban(Account.IBAN_CODE + "423456789012345678");
		HibernateUtils.insertAccount(user1, account);
		
		HibernateUtils.closeSession();
		
		HibernateUtils.openSession("bankadmin2", "admin");
		
		assertFalse("Testing Permission to READ an Account", HibernateUtils.readAccountList(user1).isEmpty());
		
		HibernateUtils.deleteAccount(account);
		List<Account> accounts = HibernateUtils.readAccountList(user1);
		assertFalse("Testing Permission to DELETE an account", accounts.contains(account));
		
		HibernateUtils.closeSession();
	}

}
