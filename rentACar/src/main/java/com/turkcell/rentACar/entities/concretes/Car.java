package com.turkcell.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id", unique = true)
	private int carId;

	@Column(name = "car_name")
	private String carName;

	@Column(name = "daily_price")
	private double dailyPrice;

	@Column(name = "model_year")
	private int modelYear;

	@Column(name = "description")
	private String carDescription;
	
	@Column(name = "kilometer_of_car")
	private int kilometerOfCar;

	@ManyToOne()
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne()
	@JoinColumn(name = "color_id")
	private Color color;

	@OneToMany(mappedBy = "carMaintenanceCar", fetch = FetchType.LAZY)
	private List<CarMaintenance> carMaintenances;

	@OneToMany(mappedBy = "rentalCar", fetch = FetchType.LAZY)
	private List<Rental> carRentals;
	
	@OneToMany(mappedBy = "damageCar", fetch = FetchType.LAZY)
	private List<Damage> carDamages;
}
