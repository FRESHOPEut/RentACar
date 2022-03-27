package com.turkcell.rentACar.business.requests.create;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {

	@NotNull
	private int carId;

	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 2, max = 150)
	private String description;

	@Nullable
	private LocalDate returnDate;

}
