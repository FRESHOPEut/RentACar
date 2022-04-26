package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceId;
	
	@Column(name = "invoice_number")
	private String invoiceNumber;
	
	@Column(name = "create_date")
	private LocalDate createDate;
	
	@Column(name = "rent_date")
	private LocalDate rentDate;
	
	@Column(name = "return_date")
	private LocalDate returnDate;
	
	@Column(name = "rented_days")
	private int rentedDays;
	
	@Column(name = "rent_total_price")
	private double rentTotalPrice;
	
	@OneToOne()
	@JoinColumn(name = "rental_id")
	private Rental invoiceRental;
	
	@ManyToOne()
	@JoinColumn(name = "customer_id")
	private Customer invoiceCustomer;
	
	@OneToOne()
	@JoinColumn(name = "payment_id")
	private Payment invoicePayment;
	
}
