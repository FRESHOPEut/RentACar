package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.car.CarDto;
import com.turkcell.rentACar.business.dtos.car.ListCarDto;
import com.turkcell.rentACar.business.requests.create.CreateCarRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Car;

public interface CarService {

	Result update(UpdateCarRequest updateCarRequest);

	Result create(CreateCarRequest createCarRequest);

	DataResult<List<ListCarDto>> listAll();

	DataResult<CarDto> getById(int carId);

	DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<ListCarDto>> findByDailyPriceLessThanEqual(double dailyPrice);

	Result delete(int carId);
	
	void updateKilometer(Car car);
}
