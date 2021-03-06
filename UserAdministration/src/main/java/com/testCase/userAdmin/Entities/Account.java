package com.testCase.userAdmin.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;


//Used to map the class to the table account
@Entity
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String IBAN_CODE = "JV20";
	
	//Map the primary key, generated by the DB
	@Id
	@GeneratedValue(generator = "account_account_id_seq", strategy = GenerationType.SEQUENCE)
	  @SequenceGenerator(
	      name = "account_account_id_seq", 
	      sequenceName = "account_account_id_seq", 
	      allocationSize = 50
	  )
	private long account_id;
	
	@Column(unique = true, nullable = false)
    private String iban;
    
	//Map a value that is automatically generated by the DB
    @Generated(GenerationTime.ALWAYS)
    @Column(nullable = false)
    private String owner;
    
	//Map the relation many to one, from the account to his user, represented by a single BankUser
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fk_user_id", nullable = false)
    private BankUser user;

	public long getAccount_id() {
		return account_id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public String getOwner() {
		return owner;
	}

	public BankUser getUser() {
		return user;
	}

	public void setUser(BankUser user) {
		this.user = user;
	}
	
    
}
