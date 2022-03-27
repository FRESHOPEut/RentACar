package com.turkcell.rentACar.business.dtos.brand;

import java.util.List;

import com.turkcell.rentACar.business.dtos.car.ListCarDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {
	
	private String brandName;
	
	private List<ListCarDto> cars;
}
