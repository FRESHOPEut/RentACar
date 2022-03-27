package com.turkcell.rentACar.business.requests.create;

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
public class CreateAdditionalServiceRequest {

	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 3 , max = 50)
	private String additionalServiceName;
	
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 3 , max = 250)
	private String additionalServiceDescription;
	
	@NotNull
	@Min(0)
	@Max(1000)
	private double additionalServiceDailyPrice;

}
