package com.turkcell.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "additional_service")
public class AdditionalService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "additional_service_id", unique = true)
	private int additionalServiceId;

	@Column(name = "additional_service_name")
	private String additionalServiceName;

	@Column(name = "additional_service_description")
	private String additionalServiceDescription;
	
	@Column(name = "additional_service_daily_price")
	private double additionalServiceDailyPrice;

	@ManyToMany(mappedBy = "rentalAdditionalServices")
	private List<Rental> additionalServiceRental;
}
