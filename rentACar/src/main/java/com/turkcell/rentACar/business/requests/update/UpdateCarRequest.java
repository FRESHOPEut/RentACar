package com.turkcell.rentACar.business.requests.update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {

	@NotNull
	private int carId;

	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 2, max = 50)
	private String carName;

	@NotNull
	@Min(0)
	@Max(2000)
	private double dailyPrice;

	@NotNull
	@Min(2000)
	@Max(2022)
	private int modelYear;

	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 2, max = 150)
	private String description;
	
	@NotNull
	@Min(0)
	private int kilometerOfCar;

	@NotNull
	private int brandId;

	@NotNull
	private int colorId;
}
