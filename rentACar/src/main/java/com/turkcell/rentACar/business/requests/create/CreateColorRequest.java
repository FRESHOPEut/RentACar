package com.turkcell.rentACar.business.requests.create;

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
public class CreateColorRequest {

	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 2, max = 50)
	private String colorName;
}
