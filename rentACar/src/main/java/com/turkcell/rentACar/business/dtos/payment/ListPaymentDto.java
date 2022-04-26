package com.turkcell.rentACar.business.dtos.payment;

import com.turkcell.rentACar.business.dtos.creditCard.CreditCardDto;
import com.turkcell.rentACar.business.dtos.rental.RentalDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPaymentDto {

	private int paymentId;
	
	private RentalDto paymentRental;
	
	private CreditCardDto paymentCard;
}
