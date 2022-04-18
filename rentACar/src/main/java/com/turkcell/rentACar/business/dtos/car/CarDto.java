package com.turkcell.rentACar.business.dtos.car;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACar.business.dtos.damage.ListDamageDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
	
	@JsonIgnore
	private int carId;

	private String carName;
	
	private double dailyPrice;
	
	private int modelYear;
	
	private String description;
	
	private int kilometerOfCar;
	
	@JsonIgnore
	private String brandId;
	
	private String brandName;
	
	@JsonIgnore
	private String colorId;
	
	private String colorName;
	
	private List<ListDamageDto> damages;
}
