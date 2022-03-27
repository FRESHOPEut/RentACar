package com.turkcell.rentACar.business.requests.update;

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
public class UpdateAdditionalServiceRequest {
	
	@NotNull
	private int additionalServiceId;

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
	private double additionalServiceDailyPrice;
}
