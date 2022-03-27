package com.turkcell.rentACar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "damages")
public class Damage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "damage_id")
	private int damageId;
	
	@Column(name = "damage_description")
	private String damageDescription;
	
	@ManyToOne()
	@JoinColumn(name = "car_id")
	private Car damageCar;
}
