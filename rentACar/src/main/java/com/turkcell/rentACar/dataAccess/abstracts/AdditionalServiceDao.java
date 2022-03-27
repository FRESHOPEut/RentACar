package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.AdditionalService;

@Repository
public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {

	AdditionalService findByAdditionalServiceId(int id);

	boolean existsByAdditionalServiceName(String name);
	
	List<AdditionalService> findAllByAdditionalServiceRental_RentalId(int rentalId);
}
