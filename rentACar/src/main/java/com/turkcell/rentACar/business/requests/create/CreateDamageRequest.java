package com.turkcell.rentACar.business.requests.create;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDamageRequest {

	@NotNull
	@Size(min = 3, max = 250)
	private String damageDescription;
	
	@NotNull
	private int damageCarId;
}
