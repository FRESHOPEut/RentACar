package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.creditCard.CreditCardDto;
import com.turkcell.rentACar.business.dtos.creditCard.ListCreditCardDto;
import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CreditCardService {

	Result update(UpdateCreditCardRequest updateCreditCardRequest);
	
	Result create(CreateCreditCardRequest createCreditCardRequest);
	
	DataResult<List<ListCreditCardDto>> listAll();
	
	DataResult<CreditCardDto> getById(int creditCardId);
	
	DataResult<CreditCardDto> getByCreditCardNumber(String creditCardNumber);
	
	DataResult<List<ListCreditCardDto>> getAllSorted(Sort.Direction direction);
	
	DataResult<List<ListCreditCardDto>> getAllPaged(int pageNo, int pageSize);
	
	Result delete(int creditCardId);
}
