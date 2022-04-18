package com.turkcell.rentACar.business.outServices;

import org.springframework.stereotype.Service;

@Service
public class AkbankManager{

	public boolean makePayment(String cardNumber, String cardCvv) {
		
		int lastDigits = Integer.valueOf(cardNumber.substring(13));
		int cvv = Integer.valueOf(cardCvv);
		
		if(lastDigits+cvv == 979) {
			
			return true;
		}
		
		return false;
	}

}
