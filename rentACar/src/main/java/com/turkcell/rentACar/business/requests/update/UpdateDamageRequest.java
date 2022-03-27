package com.turkcell.rentACar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDamageRequest {
	
	@NotNull
	private int damageId;

	@NotNull
	@Size(min = 3, max = 250)
	private String damageDescription;
}
