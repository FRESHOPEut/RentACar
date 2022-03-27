package com.turkcell.rentACar.business.dtos.corporateCustomer;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCorporateCustomerDto {
	
	private int corporateCustomerId;

	private String corporateName;

	private String taxNo;
	
	private LocalDate registeredDate;

	private String email;

	private String password;
}
