package com.turkcell.rentACar.business.requests.update;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.turkcell.rentACar.business.dtos.additionalService.AdditionalServiceIdDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {

	@NotNull
	private int rentalId;

	@NotNull
	private int carId;
	
	@NotNull
	private int returnKilometer;

	@NotNull
	private int customerId;

	@Nullable
	private LocalDate returnDate;

	@NotNull
	private int currentCityPlate;

	@Nullable
	private int returnCityPlate;

	@Nullable
	private List<AdditionalServiceIdDto> additionalServicesIds;
}
