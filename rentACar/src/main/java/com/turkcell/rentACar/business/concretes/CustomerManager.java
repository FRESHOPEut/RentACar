package com.turkcell.rentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService{

	private CustomerDao customerDao;
	
	@Autowired
	public CustomerManager(CustomerDao customerDao) {
		
		this.customerDao = customerDao;
	}
	
	@Override
	public Customer setByCustomerId(int customerId) {
		
		Customer customer = this.customerDao.getById(customerId);
		
		if (customer == null) {
			return null;
		}
		
		return customer;
	}
	
	public void checkEmailExists(String email) {
		
		if(this.customerDao.existsByEmail(email)) {
			
			throw new BusinessException(Messages.EMAILEXISTS);
		}
	}
}