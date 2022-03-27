package com.turkcell.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cities")
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "city_plate", unique = true)
	private int cityPlate;
	
	@Column(name = "city_name")
	private String cityName;
	
	@OneToMany(mappedBy = "currentCity", fetch = FetchType.LAZY)
	private List<Rental> rentalCurrentCities;
	
	@OneToMany(mappedBy = "returnCity", fetch = FetchType.LAZY)
	private List<Rental> rentalReturnCities;
}
