package com.turkcell.rentACar.business.dtos.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCityDto {

	private int cityPlate;
	
	private String cityName;
}
