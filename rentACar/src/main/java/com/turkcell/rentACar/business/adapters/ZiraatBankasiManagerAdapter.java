package com.turkcell.rentACar.business.adapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.outServices.ZiraatBankasiManager;
import com.turkcell.rentACar.core.utilities.posServiceAdapter.abstracts.PosService;
import com.turkcell.rentACar.entities.concretes.CreditCard;

@Service
@Primary
public class ZiraatBankasiManagerAdapter implements PosService{

	@Override
	public boolean checkCardIsActive(CreditCard creditCard) {
		
		ZiraatBankasiManager ziraatBankasi = new ZiraatBankasiManager();
		
		return ziraatBankasi.makePayment(creditCard.getCreditCardNumber(), creditCard.getCreditCardCvv(),
				creditCard.getCreditCardExpirationDate(), creditCard.getCreditCardOwnerName());
	}

}
