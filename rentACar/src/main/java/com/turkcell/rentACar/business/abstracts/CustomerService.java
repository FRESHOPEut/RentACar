package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.entities.concretes.Customer;

public interface CustomerService {
	
	Customer setByCustomerId(int customerId);
	
	void checkCustomerExists(int customerId);
	
	void checkEmailExists(String email);
}
