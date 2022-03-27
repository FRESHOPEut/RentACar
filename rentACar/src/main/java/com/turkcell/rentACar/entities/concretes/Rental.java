package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rentals")
public class Rental{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rental_id")
	private int rentalId;

	@ManyToOne()
	@JoinColumn(name = "car_id")
	private Car rentalCar;
	
	@Column(name = "rented_kilometer")
	private int rentedKilometer;
	
	@Column(name = "return_kilometer")
	private int returnKilometer;

	@ManyToOne()
	@JoinColumn(name = "customer_id")
	private Customer rentalCustomer;

	@Column(name = "rental_date")
	private LocalDate rentalDate;

	@Column(name = "return_date")
	private LocalDate returnDate;
	
	@ManyToOne()
	@JoinColumn(name = "current_city")
	private City currentCity;
	
	@ManyToOne()
	@JoinColumn(name = "return_city")
	private City returnCity;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ordered_additional_service",
	joinColumns = @JoinColumn(name = "rental_id"),
	inverseJoinColumns = @JoinColumn(name = "additional_service_id"))
	private List<AdditionalService> rentalAdditionalServices;
	
	@Column(name = "rental_daily_price")
	private double rentalTotalDailyPrice;
	
	@OneToOne(mappedBy = "invoiceRental")
	private Invoice rentalInvoice;
	
	@OneToMany(mappedBy = "paymentRental", fetch = FetchType.LAZY)
	private List<Payment> rentalPayments;
}
