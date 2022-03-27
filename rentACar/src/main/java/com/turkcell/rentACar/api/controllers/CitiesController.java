package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.city.CityDto;
import com.turkcell.rentACar.business.dtos.city.ListCityDto;
import com.turkcell.rentACar.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

	private CityService cityService;

	@Autowired
	public CitiesController(CityService cityService) {
		
		this.cityService = cityService;
	}

	@GetMapping("/listAll")
	public DataResult<List<ListCityDto>> listAll(){
		
		return this.cityService.listAll();
	}

	@GetMapping("/getByCityPlate")
	DataResult<CityDto> getByCityPlate(int cityPlate){
		
		return this.cityService.getByCityPlate(cityPlate);
	}

	@GetMapping("/getAllSorted")
	public DataResult<List<ListCityDto>> getAllSorted(Sort.Direction direction){
		
		return this.cityService.getAllSorted(direction);
	}

	@GetMapping("/getAllPaged")
	public DataResult<List<ListCityDto>> getAllPaged(int pageNo, int pageSize){
		
		return this.cityService.getAllPaged(pageNo, pageSize);
	}
}
