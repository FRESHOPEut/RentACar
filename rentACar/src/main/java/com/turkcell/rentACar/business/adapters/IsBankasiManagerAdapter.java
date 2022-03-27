package com.turkcell.rentACar.business.adapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.outServices.IsBankasiManager;
import com.turkcell.rentACar.core.utilities.posServiceAdapter.abstracts.PosService;
import com.turkcell.rentACar.entities.concretes.CreditCard;

@Service
public class IsBankasiManagerAdapter implements PosService{

	@Override
	public boolean checkCardIsActive(CreditCard creditCard) {
		
		IsBankasiManager isBankasi = new IsBankasiManager();
		
		return isBankasi.makePayment(creditCard.getCreditCardNumber(), creditCard.getCreditCardCvv(),
				creditCard.getCreditCardExpirationDate());
	}

}
