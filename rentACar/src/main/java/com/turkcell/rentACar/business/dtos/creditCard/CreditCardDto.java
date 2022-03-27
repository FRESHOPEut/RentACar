package com.turkcell.rentACar.business.dtos.creditCard;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {

	private String creditCardNumber;
	
	private String creditCardCvv;
	
	private String creditCardOwnerName;
	
	private LocalDate creditCardExpirationDate;
}
