package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.rental.ListRentalDto;
import com.turkcell.rentACar.business.dtos.rental.RentalDto;
import com.turkcell.rentACar.business.requests.create.CreateRentalRequest;
import com.turkcell.rentACar.business.requests.update.UpdateRentalRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface RentalService {

	Result update(UpdateRentalRequest updateRentalRequest);

	Result create(CreateRentalRequest createRentalRequest);

	DataResult<List<ListRentalDto>> listAll();

	DataResult<RentalDto> getById(int rentalId);

	DataResult<List<ListRentalDto>> getAllPaged(int pageNo, int pageSize);

	Result delete(int rentalId);
	
	void checkCarAlreadyRented(int carId);
}
