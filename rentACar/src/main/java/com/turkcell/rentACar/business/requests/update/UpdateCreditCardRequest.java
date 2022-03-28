package com.turkcell.rentACar.business.requests.update;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {

	@NotNull
	private int creditCardId;
	
	@NotNull
	private int customerId;
	
	@NotNull
	@Size(min = 16, max = 16)
	private String creditCardNumber;
	
	@NotNull
	@Size(min = 3, max = 3)
	private String creditCardCvv;
	
	@NotNull
	@Size(min = 5, max = 100)
	private String creditCardOwnerName;
	
	@NotNull
	private LocalDate creditCardExpirationDate;
}
