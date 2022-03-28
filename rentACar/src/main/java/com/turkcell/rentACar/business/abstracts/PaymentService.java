package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.payment.ListPaymentDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentDto;
import com.turkcell.rentACar.business.requests.create.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.create.CreatePaymentWithSavedCardRequest;
import com.turkcell.rentACar.business.requests.update.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PaymentService {

	Result update(UpdatePaymentRequest updatePaymentRequest);
	
	Result create(CreatePaymentRequest createPaymentRequest, boolean saveCard);
	
	Result createWithSavedCard(CreatePaymentWithSavedCardRequest createPaymentWithSavedCardRequest);
	
	DataResult<List<ListPaymentDto>> listAll();
	
	DataResult<PaymentDto> getByPaymentId(int paymentId);
	
	DataResult<List<ListPaymentDto>> getAllSorted(Sort.Direction direction);
	
	DataResult<List<ListPaymentDto>> getAllPaged(int pageNo, int pageSize);
	
	Result delete(int paymentId);
}
