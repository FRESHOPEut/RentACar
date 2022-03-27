package com.turkcell.rentACar.business.dtos.rental;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.car.CarDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListRentalDto {

	private int rentalId;
	
	private CarDto car;
	
	private int customerId;
	
	private LocalDate rentalDate;
	
	private LocalDate returnDate;
	
	private String currentCity;
	
	private String returnCity;
	
	private List<ListAdditionalServiceDto> additionalServices;
	
	private double totalDailyPrice;
}
