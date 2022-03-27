package com.turkcell.rentACar.business.dtos.individualCustomer;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListIndividualCustomerDto {
	
	private int individualCustomerId;

	private String nationalIdentity;
	
	private String firstname;
	
	private String lastname;
	
	private LocalDate registeredDate;
	
	private String email;
	
	private String password;
}
