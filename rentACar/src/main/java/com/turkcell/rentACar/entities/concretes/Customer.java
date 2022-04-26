package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@EqualsAndHashCode(callSuper = false)
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {
	
	@Column(name = "registered_date")
	private LocalDate registeredDate;

	@OneToMany(mappedBy = "rentalCustomer", fetch = FetchType.LAZY)
	private List<Rental> customerRentals;
	
	@OneToMany(mappedBy = "invoiceCustomer", fetch = FetchType.LAZY)
	private List<Invoice> customerInvoices;
	
	@OneToMany(mappedBy = "creditCardCustomer", fetch = FetchType.LAZY)
	private List<CreditCard> customerCreditCards;
}
