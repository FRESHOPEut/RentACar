package com.turkcell.rentACar.business.adapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.outServices.AkbankManager;
import com.turkcell.rentACar.core.utilities.posServiceAdapter.abstracts.PosService;
import com.turkcell.rentACar.entities.concretes.CreditCard;

@Service
public class AkbankManagerAdapter implements PosService{
	
	@Override
	public boolean checkCardIsActive(CreditCard creditCard) {
		
		AkbankManager akbank = new AkbankManager();
		
		return akbank.makePayment(creditCard.getCreditCardNumber(), creditCard.getCreditCardCvv());
	}

}
