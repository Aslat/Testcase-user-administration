package com.testCase.userAdmin;

public class ValidatorUtils {
	
	public static boolean checkName(String name) {
		if((name.isEmpty()) || (name == null) || (!name.matches("^[a-zA-Z]*$"))) {
			System.out.println("The data introduced must not be empty or contain numbers");
			return false;
		}
		return true;
	}

	//Validation for the IBAN will remain really simple, but here we should made complex validations related with the bank, office....
	public static boolean checkIban(String iban) {
		if (iban.isEmpty() || iban == null) {
			System.out.println("\nThe iban must not be empty\n");
			return false;
		}
		if (!iban.matches("[0-9]+")) {
			System.out.println("The iban must only contain numbers");
			return false;
		}
		if (iban.length() < 18 || iban.length() > 18) {
			System.out.println("The iban must has a length of 18 characters");
			return false;
		}
		return true;
	}
		
	
}
