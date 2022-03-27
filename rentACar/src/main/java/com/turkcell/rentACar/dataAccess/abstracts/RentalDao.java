package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACar.entities.concretes.Rental;

public interface RentalDao extends JpaRepository<Rental, Integer> {

	Rental findByRentalId(int rentalId);
	
	List<Rental> getRentalByRentalCar_CarId(int carId);
		
}
