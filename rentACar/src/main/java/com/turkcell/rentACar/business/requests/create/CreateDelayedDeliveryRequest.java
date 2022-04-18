package com.turkcell.rentACar.business.requests.create;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.turkcell.rentACar.business.dtos.additionalService.AdditionalServiceIdDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDelayedDeliveryRequest {

	@NotNull
	private int carId;

	@NotNull
	private int customerId;
	
	@NotNull
	private LocalDate rentDate;
	
	@NotNull
	private LocalDate returnDate;
	
	@NotNull
	private int currentCityPlate;
	
	@Nullable
	private int returnCityPlate;

	@Nullable
	private List<AdditionalServiceIdDto> additionalServicesIds;
	
	private double totalDailyPrice;
}
