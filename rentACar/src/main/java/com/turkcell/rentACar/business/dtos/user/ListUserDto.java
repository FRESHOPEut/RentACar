package com.turkcell.rentACar.business.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUserDto {

	private int userId;
	
	private String email;
	
	private String password;
}
