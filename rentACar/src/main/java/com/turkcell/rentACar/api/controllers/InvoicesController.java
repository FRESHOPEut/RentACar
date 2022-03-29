package com.turkcell.rentACar.api.controllers;

import java.time.LocalDate;
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

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceDto;
import com.turkcell.rentACar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.rentACar.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

	private InvoiceService invoiceService;

	@Autowired
	public InvoicesController(InvoiceService invoiceService) {

		this.invoiceService = invoiceService;
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest) {
		
		return this.invoiceService.update(updateInvoiceRequest);
	}
	
	@PostMapping("/create")
	public Result create(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) {
		
		return this.invoiceService.create(createInvoiceRequest);
	}
	
	@GetMapping("/listAll")
	public DataResult<List<ListInvoiceDto>> listAll(){
		
		return this.invoiceService.listAll();
	}
	
	@GetMapping("/getById")
	public DataResult<InvoiceDto> getByInvoiceNumber(@RequestParam int invoiceId){
		
		return this.invoiceService.getByInvoiceId(invoiceId);
	}
	
	@GetMapping("/getByInvoiceNumber")
	public DataResult<InvoiceDto> getByInvoiceNo(@RequestParam long invoiceNumber){
		
		return this.invoiceService.getByInvoiceNumber(invoiceNumber);
	}
	
	@GetMapping("/getAllPaged")
	public DataResult<List<ListInvoiceDto>> getAllPaged(int pageNo, int pageSize){
		
		return this.invoiceService.getAllPaged(pageNo, pageSize);
	}
	
	@GetMapping("/getByDateOfBetween")
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate){
		
		return this.invoiceService.getByDateofBetween(startDate, finishDate);
	}
	
	@GetMapping("/getInvoiceByCustomerId")
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomerId(int customerId){
		
		return this.invoiceService.getInvoiceByCustomerId(customerId);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestParam int invoiceNo) {
		
		return this.invoiceService.delete(invoiceNo);
	}
}
