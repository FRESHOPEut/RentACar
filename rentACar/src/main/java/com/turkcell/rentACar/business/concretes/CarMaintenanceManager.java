package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.business.dtos.car.CarDto;
import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.carMaintenance.ListCarMaintenanceDto;
import com.turkcell.rentACar.business.requests.create.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.abstracts.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACar.entities.concretes.Car;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	private CarService carService;

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao,
			ModelMapperService modelMapperService, @Lazy RentalService rentalService, CarService carService) {
		
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
		this.carService = carService;
	}

	@Override
	@Transactional
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest){
		

		checkCarMaintenanceIdExists(updateCarMaintenanceRequest.getCarMaintenanceId());
		this.rentalService.checkCarAlreadyRented(updateCarMaintenanceRequest.getCarId());
		checkCarIdExists(updateCarMaintenanceRequest.getCarId());

		LocalDate date = getById(updateCarMaintenanceRequest.getCarMaintenanceId()).getData().getMaintenanceDate();
		CarDto carDto = this.carService.getById(updateCarMaintenanceRequest.getCarId()).getData();
		Car car = this.modelMapperService.forDto().map(carDto, Car.class);
		updateCarMaintenanceRequest.setMaintenanceDate(date);
		CarMaintenance carMaintenance = this.modelMapperService.forRequest()
			.map(updateCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setCarMaintenanceCar(car);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessDataResult<UpdateCarMaintenanceRequest>(updateCarMaintenanceRequest,
			Messages.CARMAINTENANCEUPDATED + carMaintenance.getCarMaintenanceDescription());
	}

	@Override
	@Transactional
	public Result create(CreateCarMaintenanceRequest createCarMaintenanceRequest){
		
		LocalDate localDate = LocalDate.now();
		
		this.rentalService.checkCarAlreadyRented(createCarMaintenanceRequest.getCarId());
		checkCarAlreadyMaintenanced(createCarMaintenanceRequest.getCarId());
		checkCarIdExists(createCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest()
			.map(createCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setMaintenanceDate(localDate);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessDataResult<CreateCarMaintenanceRequest>(createCarMaintenanceRequest,
			Messages.CARMAINTENANCEADDED + carMaintenance.getCarMaintenanceDescription());
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> listAll(){
		
		Sort sort = Sort.by(Direction.ASC, "carMaintenanceId");
		List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAll(sort);
		List<ListCarMaintenanceDto> listCarMaintenanceDtos = carMaintenances.stream()
			.map(carMaintenance -> this.modelMapperService.forDto()
			.map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(listCarMaintenanceDtos,
			Messages.CARMAINTENANCESLISTED);
	}

	@Override
	public DataResult<CarMaintenanceDto> getById(int carMaintenanceId){
		
		checkCarMaintenanceIdExists(carMaintenanceId);
		
		CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);
		CarMaintenanceDto carMaintenanceDto = this.modelMapperService.forDto()
			.map(carMaintenance, CarMaintenanceDto.class);
		
		return new SuccessDataResult<CarMaintenanceDto>(carMaintenanceDto, Messages.CARMAINTENANCEGETTEDBYID);
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAllPaged(int pageNo, int pageSize){
		
		checkPageNoAndPageSize(pageNo, pageSize);
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAll(pageable).getContent();
		List<ListCarMaintenanceDto> listCarMaintenanceDtos = carMaintenances.stream()
			.map(carMaintenance -> this.modelMapperService.forDto()
			.map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(listCarMaintenanceDtos,
			Messages.DATAINPAGE + Integer.toString(pageNo) + Messages.ISLISTEDWITHDATASIZE
			+ Integer.toString(pageSize));
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getByCarId(int carId){
		
		checkCarIdExists(carId);
		
		List<CarMaintenance> carMaintenances = this.carMaintenanceDao
			.getCarMaintenanceByCarMaintenanceCar_CarId(carId);
		List<ListCarMaintenanceDto> listCarMaintenanceDtos = carMaintenances.stream()
			.map(carMaintenance -> this.modelMapperService.forDto()
			.map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarMaintenanceDto>>(listCarMaintenanceDtos,
				Messages.CARMAINTENANCESGETTEDBYCARID);
	}

	@Override
	public Result delete(int carMaintenanceId){
		
		checkCarMaintenanceIdExists(carMaintenanceId);
		
		String descriptionBeforeDelete = this.carMaintenanceDao.findByCarMaintenanceId(carMaintenanceId)
				.getCarMaintenanceDescription();
		this.carMaintenanceDao.deleteById(carMaintenanceId);
		
		return new SuccessResult(Messages.CARMAINTENANCEDELETED + descriptionBeforeDelete);
	}

	private void checkCarMaintenanceIdExists(int carMaintenanceId){
		
		if (!this.carMaintenanceDao.existsById(carMaintenanceId)) {
			
			throw new BusinessException(Messages.CARMAINTENANCENOTFOUND);
		}
	}

	private void checkCarIdExists(int carId){
		
		if (!this.carService.getById(carId).isSuccess()) {
			
			throw new BusinessException(Messages.CARMAINTENANCECARIDNOTFOUND);
		}
	}
	
	public void checkCarAlreadyMaintenanced(int carId){
		
		List<CarMaintenance> carMaintenances = this.carMaintenanceDao
				.getCarMaintenanceByCarMaintenanceCar_CarId(carId);
		
		if (!carMaintenances.isEmpty()) {
			
			for (CarMaintenance carMaintenance : carMaintenances) {
				
				if (carMaintenance.getReturnDate() == null) {
					
					throw new BusinessException(Messages.CARMAINTENANCESTILLMAINTENANCED);
				}
			}
		}
	}
	
	private void checkPageNoAndPageSize(int pageNo, int pageSize) {
		
		if(pageNo <= 0) {
			
			throw new BusinessException(Messages.PAGENOCANNOTLESSTHANZERO);
		}else if(pageSize <= 0) {
			
			throw new BusinessException(Messages.PAGESIZECANNOTLESSTHANZERO);
		}
	}
}