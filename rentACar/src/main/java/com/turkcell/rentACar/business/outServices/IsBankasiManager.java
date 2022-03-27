package com.turkcell.rentACar.business.outServices;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class IsBankasiManager{

	public boolean makePayment(String cardNumber, String cardCvv, LocalDate cardExpirationDate) {
		
		int lastDigits = Integer.valueOf(cardNumber.substring(13));
		int cvv = Integer.valueOf(cardCvv);
		
		if(lastDigits+cvv == 989) {
			
			System.out.println("İş bankası ile ödendi");
			return true;
		}
		
		return false;
	}

}
