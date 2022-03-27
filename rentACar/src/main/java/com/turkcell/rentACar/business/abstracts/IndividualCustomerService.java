package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.individualCustomer.ListIndividualCustomerDto;
import com.turkcell.rentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface IndividualCustomerService {

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);

	Result create(CreateIndividualCustomerRequest createIndividualCustomerRequest);

	DataResult<List<ListIndividualCustomerDto>> listAll();

	DataResult<IndividualCustomerDto> getByIndividualCustomerId(int individualCustomerId);

	DataResult<List<ListIndividualCustomerDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<ListIndividualCustomerDto>> getAllPaged(int pageNo, int pageSize);

	Result delete(int individualCustomerId);
}
