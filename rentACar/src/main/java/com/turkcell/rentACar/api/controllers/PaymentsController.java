package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.dtos.payment.ListPaymentDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentDto;
import com.turkcell.rentACar.business.requests.create.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.create.CreatePaymentWithSavedCardRequest;
import com.turkcell.rentACar.business.requests.update.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

	private PaymentService paymentService;

	public PaymentsController(PaymentService paymentService) {

		this.paymentService = paymentService;
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdatePaymentRequest updatePaymentRequest) {
		
		return this.paymentService.update(updatePaymentRequest);
	}
	
	@PostMapping("/create")
	public Result create(@RequestBody @Valid CreatePaymentRequest createPaymentRequest, boolean saveCard) {
		
		return this.paymentService.create(createPaymentRequest, saveCard);
	}
	
	@PostMapping("/createWithSavedCard")
	public Result createWithSavedCard(CreatePaymentWithSavedCardRequest createPaymentWithSavedCardRequest) {
		
		return this.paymentService.createWithSavedCard(createPaymentWithSavedCardRequest);
	}
	
	@GetMapping("/listAll")
	public DataResult<List<ListPaymentDto>> listAll(){
		
		return this.paymentService.listAll();
	}
	
	@GetMapping("/getById")
	public DataResult<PaymentDto> getById(@RequestParam int paymentId){
		
		return this.paymentService.getByPaymentId(paymentId);
	}
	
	@GetMapping("/getAllSorted")
	public DataResult<List<ListPaymentDto>> getAllSorted(Sort.Direction direction){
		
		return this.paymentService.getAllSorted(direction);
	}
	
	@GetMapping("/getAllPaged")
	public DataResult<List<ListPaymentDto>> getAllPaged(int pageNo, int pageSize){
		
		return this.paymentService.getAllPaged(pageNo, pageSize);
	}
	
	public Result delete(@RequestParam int paymentId) {
		
		return this.paymentService.delete(paymentId);
	}
}
