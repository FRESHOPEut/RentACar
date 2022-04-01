package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.business.dtos.car.CarDto;
import com.turkcell.rentACar.business.dtos.rental.ListRentalDto;
import com.turkcell.rentACar.business.dtos.rental.RentalDto;
import com.turkcell.rentACar.business.requests.create.CreateCarRequest;
import com.turkcell.rentACar.business.requests.create.CreateRentalRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.rentACar.business.requests.update.UpdateRentalRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.abstracts.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;
import com.turkcell.rentACar.entities.concretes.Car;
import com.turkcell.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private AdditionalServiceService additionalServiceService;
	private CustomerService customerService;
	
	@Autowired
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
			@Lazy CarMaintenanceService carMaintenanceService, CarService carService,
			AdditionalServiceService additionalServiceService, CustomerService customerService) {
		
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.additionalServiceService = additionalServiceService;
		this.customerService = customerService;
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest){
		
		checkRentalIdExists(updateRentalRequest.getRentalId());
		this.carMaintenanceService.checkCarAlreadyMaintenanced(updateRentalRequest.getCarId());
		checkCarAlreadyRented(updateRentalRequest.getCarId());
		checkCarIdExists(updateRentalRequest.getCarId());
		
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		calculateTotalDailyPrice(rental.getRentalId());
		updateCarKilometer(updateRentalRequest);
		this.rentalDao.save(rental);
		
		return new SuccessDataResult<UpdateRentalRequest>(updateRentalRequest,
			Messages.RENTALUPDATED + Integer.toString(rental.getRentalId()) + Messages.AND
			+ Messages.RENTALTOTALPRICE + Double.toString(rental.getRentalTotalDailyPrice()));
	}

	@Override
	@Transactional
	public Result create(CreateRentalRequest createRentalRequest){
		
		LocalDate date = LocalDate.now();
		
		this.carMaintenanceService.checkCarAlreadyMaintenanced(createRentalRequest.getCarId());
		checkCarAlreadyRented(createRentalRequest.getCarId());
		checkCarIdExists(createRentalRequest.getCarId());
		
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		CarDto carDto = this.carService.getById(createRentalRequest.getCarId()).getData();
		rental.setRentalCustomer(this.customerService.setByCustomerId(createRentalRequest.getCustomerId()));
		rental.setRentalDate(date);
		rental.setRentedKilometer(carDto.getKilometerOfCar());
		this.rentalDao.save(rental);

		rental.setRentalTotalDailyPrice(calculateTotalDailyPrice(rental.getRentalId()));
		this.rentalDao.save(rental);
		
		return new SuccessDataResult<CreateRentalRequest>(createRentalRequest,
			Messages.RENTALADDED + Messages.AND + Messages.RENTALTOTALPRICE
			+ Double.toString(rental.getRentalTotalDailyPrice()));
	}

	@Override
	public DataResult<List<ListRentalDto>> listAll(){
		
		List<Rental> rentals = this.rentalDao.findAll();
		List<ListRentalDto> listRentalDtos = rentals.stream()
			.map(rental -> this.modelMapperService.forDto().map(rental, ListRentalDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListRentalDto>>(listRentalDtos, Messages.RENTALSLISTED);
	}

	@Override
	public DataResult<RentalDto> getById(int rentalId){
		
		checkRentalIdExists(rentalId);
		
		Rental rental = this.rentalDao.getById(rentalId);
		RentalDto rentalDto = this.modelMapperService.forDto().map(rental, RentalDto.class);
		
		return new SuccessDataResult<RentalDto>(rentalDto, Messages.RENTALGETTEDBYID);
	}

	@Override
	public DataResult<List<ListRentalDto>> getAllPaged(int pageNo, int pageSize){
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Rental> rentals = this.rentalDao.findAll(pageable).getContent();
		List<ListRentalDto> listRentalDtos = rentals.stream()
			.map(rental -> this.modelMapperService.forDto().map(rental, ListRentalDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListRentalDto>>(listRentalDtos, Messages.DATAINPAGE +
			Integer.toString(pageNo) + Messages.ISLISTEDWITHDATASIZE + Integer.toString(pageSize));
	}

	@Override
	public Result delete(int rentalId){
		
		checkRentalIdExists(rentalId);
		
		int rentalIdBeforeDelete = rentalId;
		this.rentalDao.deleteById(rentalId);
		
		return new SuccessResult(Messages.RENTALDELETED + Integer.toString(rentalIdBeforeDelete));
	}
	
	@Override
	public void checkCarAlreadyRented(int carId){
		
		List<Rental> rentals = this.rentalDao.getRentalByRentalCar_CarId(carId);
		
		if (!rentals.isEmpty()) {
			
			for (Rental rental : rentals) {
				
				if (rental.getReturnDate() == null) {
					
					throw new BusinessException(Messages.RENTALCARINRENT);
				}
			}
		}
	}

	private void checkRentalIdExists(int rentalId){
		
		if (!this.rentalDao.existsById(rentalId)) {
			
			throw new BusinessException(Messages.RENTALNOTFOUND);
		}
	}

	private void checkCarIdExists(int carId){
		
		if (!this.carService.getById(carId).isSuccess()) {
			
			throw new BusinessException(Messages.RENTALNOTFOUNDBYCAR);
		}
		
	}
	
	private double calculateTotalDailyPrice(int rentalId) {
		
		Rental rental = this.rentalDao.getById(rentalId);
		List<AdditionalService> additionalServices = this.additionalServiceService
			.getByRentalId(rentalId); 
		
		double totalDailyPrice = this.carService.getById(rental.getRentalCar().getCarId()).getData().getDailyPrice();
		
		for (AdditionalService additionalService : additionalServices) {
			
			totalDailyPrice += additionalService.getAdditionalServiceDailyPrice();
		}
		
		return totalDailyPrice;
	}
	
	private void updateCarKilometer(UpdateRentalRequest updateRentalRequest) {
		
		CarDto carDto = this.carService.getById(updateRentalRequest.getCarId()).getData();
		Car car = this.modelMapperService.forDto().map(carDto, Car.class);
		
		if(car.getKilometerOfCar() < updateRentalRequest.getReturnKilometer()) {
			
			car.setKilometerOfCar(updateRentalRequest.getReturnKilometer());
			UpdateCarRequest updateCarRequest = this.modelMapperService.forRequest().map(car, UpdateCarRequest.class); 
			this.carService.update(updateCarRequest);
			//this.carService.updateKilometer(car);
			//update kısmını dene olmazsa request mapping kaldır
			
		}else {
			
			throw new BusinessException(Messages.RENTALCARKILOMETER);
		}
	}
}