package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.CreditCard;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Integer>{
	
	CreditCard getByCreditCardId(int creditCardId);
	
	CreditCard getByCreditCardNumber(String creditCardNumber);
	
	List<CreditCard> getByCreditCardOwnerName(String creditCardOwnerName);
	
	boolean existsByCreditCardNumber(String creditCardNumber);
}
