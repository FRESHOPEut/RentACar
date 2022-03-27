package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.rentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {

	private CarMaintenanceService carMaintenanceService;

	@Autowired
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		
		this.carMaintenanceService = carMaintenanceService;
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest){
		
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	}

	@PostMapping("/create")
	public Result create(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest){
		
		return this.carMaintenanceService.create(createCarMaintenanceRequest);
	}

	@GetMapping("/listAll")
	public DataResult<List<ListCarMaintenanceDto>> listAll(){
		
		return this.carMaintenanceService.listAll();
	}

	@GetMapping("/getByCarMaintenanceId")
	public DataResult<CarMaintenanceDto> getById(@RequestParam int carMaintenanceId){
		
		return this.carMaintenanceService.getById(carMaintenanceId);
	}

	@GetMapping("/getByCarId")
	public DataResult<List<ListCarMaintenanceDto>> getByCarId(@RequestParam int carId){
		
		return this.carMaintenanceService.getByCarId(carId);
	}

	@GetMapping("/getAllPaged")
	DataResult<List<ListCarMaintenanceDto>> getAllPaged(int pageNo, int pageSize){
		
		return this.carMaintenanceService.getAllPaged(pageNo, pageSize);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam int carMaintenanceId){
		
		return this.carMaintenanceService.delete(carMaintenanceId);
	}
}
