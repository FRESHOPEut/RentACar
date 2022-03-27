package com.turkcell.rentACar.business.dtos.carMaintenance;

import java.time.LocalDate;

import com.turkcell.rentACar.business.dtos.car.CarDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarMaintenanceDto {

	private int carMaintenanceId;
	
	private String description;
	
	private LocalDate maintenanceDate;
	
	private LocalDate returnDate;
	
	private CarDto car;

}
