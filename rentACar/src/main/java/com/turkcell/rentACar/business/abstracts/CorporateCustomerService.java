package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.corporateCustomer.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.corporateCustomer.ListCorporateCustomerDto;
import com.turkcell.rentACar.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CorporateCustomerService {

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

	Result create(CreateCorporateCustomerRequest createCorporateCustomerRequest);

	DataResult<List<ListCorporateCustomerDto>> listAll();

	DataResult<CorporateCustomerDto> getByCorporateCustomerId(int corporateCustomerId);

	DataResult<List<ListCorporateCustomerDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<ListCorporateCustomerDto>> getAllPaged(int pageNo, int pageSize);

	Result delete(int corporateCustomerId);
}
