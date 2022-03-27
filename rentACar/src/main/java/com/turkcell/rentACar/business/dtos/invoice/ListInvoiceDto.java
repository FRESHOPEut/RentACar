package com.turkcell.rentACar.business.dtos.invoice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListInvoiceDto {
	
	private int invoiceId;

	private long invoiceNumber;
	
	private LocalDate createDate;
	
	private LocalDate rentDate;
	
	private LocalDate returnDate;
	
	private int rentedDays;
	
	private double rentTotalPrice;
	
	private int rentalId;
	
	private int customerId;
	
	private int paymentId;
}
