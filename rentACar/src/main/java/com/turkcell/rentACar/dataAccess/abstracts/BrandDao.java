package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer> {

	Brand findByBrandId(int id);

	boolean existsByBrandName(String name);
	
	Brand getByBrandName(String name);

}
