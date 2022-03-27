package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.individualCustomer.ListIndividualCustomerDto;
import com.turkcell.rentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {

	private IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		
		this.individualCustomerService = individualCustomerService;
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest){
		
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}

	@PostMapping("/create")
	public Result create(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest){
		
		return this.individualCustomerService.create(createIndividualCustomerRequest);
	}

	@GetMapping("/listAll")
	public DataResult<List<ListIndividualCustomerDto>> listAll(){
		
		return this.individualCustomerService.listAll();
	}

	@GetMapping("getByIndividualCustomerId")
	DataResult<IndividualCustomerDto> getByCorporateCustomerId(int individualCustomerId){
		
		return this.individualCustomerService.getByIndividualCustomerId(individualCustomerId);
	}

	@GetMapping("/getAllSorted")
	public DataResult<List<ListIndividualCustomerDto>> getAllSorted(Sort.Direction direction){
		
		return this.individualCustomerService.getAllSorted(direction);
	}

	@GetMapping("/getAllPaged")
	public DataResult<List<ListIndividualCustomerDto>> getAllPaged(int pageNo, int pageSize){
		
		return this.individualCustomerService.getAllPaged(pageNo, pageSize);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam int individualCustomerId){
		
		return this.individualCustomerService.delete(individualCustomerId);
	}
}
