package com.turkcell.rentACar.business.dtos.damage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListDamageDto {

	private int damageId;
	
	private String damageDescription;
}
