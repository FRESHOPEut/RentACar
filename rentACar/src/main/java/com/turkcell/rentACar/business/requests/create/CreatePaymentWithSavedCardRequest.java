package com.turkcell.rentACar.business.requests.create;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentWithSavedCardRequest {
	
	@JsonIgnore
	private int paymentId;

	@NotNull
	private int rentalId;
	
	@NotNull
	private int creditCardId;
}
