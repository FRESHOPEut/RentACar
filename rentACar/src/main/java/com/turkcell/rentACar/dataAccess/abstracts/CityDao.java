package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.City;

@Repository
public interface CityDao extends JpaRepository<City, Integer> {

	City findByCityPlate(int cityPlate);
	
	boolean existsByCityName(String cityName);
	
	City getByCityName(int cityName);
}
