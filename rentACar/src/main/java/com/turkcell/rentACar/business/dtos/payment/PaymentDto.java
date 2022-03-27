package com.turkcell.rentACar.business.dtos.payment;

import com.turkcell.rentACar.business.dtos.invoice.InvoiceDto;
import com.turkcell.rentACar.business.dtos.rental.RentalDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
	
	private RentalDto payedRental;
	
	private InvoiceDto payedInvoice;
}
