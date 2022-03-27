package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.additionalService.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.rentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.AdditionalService;

public interface AdditionalServiceService {

	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);

	Result create(CreateAdditionalServiceRequest createAdditionalServiceRequest);

	DataResult<List<ListAdditionalServiceDto>> listAll();

	DataResult<AdditionalServiceDto> getById(int additionalServiceId);

	DataResult<List<ListAdditionalServiceDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<ListAdditionalServiceDto>> getAllPaged(int pageNo, int pageSize);

	Result delete(int additionalServiceId);
	
	List<AdditionalService> getByRentalId(int rentalId);
}
