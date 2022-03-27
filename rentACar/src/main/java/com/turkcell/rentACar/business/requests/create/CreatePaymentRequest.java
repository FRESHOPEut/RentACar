package com.turkcell.rentACar.business.requests.create;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	
	@NotNull
	private int rentalId;
	
	@NotNull
	private CreateCreditCardRequest creditCard;
}
