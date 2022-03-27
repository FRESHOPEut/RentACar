package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.rentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarMaintenanceService {

	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);

	Result create(CreateCarMaintenanceRequest createCarMaintenanceRequest);

	DataResult<List<ListCarMaintenanceDto>> listAll();

	DataResult<CarMaintenanceDto> getById(int carMaintenanceId);

	DataResult<List<ListCarMaintenanceDto>> getAllPaged(int pageNo, int pageSize);
	
	DataResult<List<ListCarMaintenanceDto>> getByCarId(int carId);

	Result delete(int carMaintenanceId);
	
	void checkCarAlreadyMaintenanced(int carId);
}
