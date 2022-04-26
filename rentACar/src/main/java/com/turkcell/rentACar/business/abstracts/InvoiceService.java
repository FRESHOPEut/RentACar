package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.invoice.InvoiceDto;
import com.turkcell.rentACar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.rentACar.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface InvoiceService {

	Result update(UpdateInvoiceRequest updateInvoiceRequest);
	
	Result create(CreateInvoiceRequest createInvoiceRequest);
	
	DataResult<List<ListInvoiceDto>> listAll();
	
	DataResult<InvoiceDto> getByInvoiceId(int invoiceId);
	
	DataResult<InvoiceDto> getByInvoiceNumber(String invoiceNumber);
	
	DataResult<List<ListInvoiceDto>> getAllPaged(int pageNo, int pageSize);
	
	DataResult<List<ListInvoiceDto>> getByDateofBetween(LocalDate startDate, LocalDate finishDate);
	
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomerId(int customerId);
	
	Result delete(int invoiceId);
}
