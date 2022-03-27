package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Damage;

@Repository
public interface DamageDao extends JpaRepository<Damage, Integer>{

	boolean existsByDamageDescription(String damageDescription);
	
	boolean existsByDamageId(int carDamageId);
}
