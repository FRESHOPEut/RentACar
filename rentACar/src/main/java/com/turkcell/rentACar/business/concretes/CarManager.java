package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.business.dtos.car.CarDto;
import com.turkcell.rentACar.business.dtos.car.ListCarDto;
import com.turkcell.rentACar.business.requests.create.CreateCarRequest;
import com.turkcell.rentACar.business.requests.update.UpdateCarRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.abstracts.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private ColorService colorService;
	private BrandService brandService;
	
	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService,	ColorService colorService, BrandService brandService) {
		
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.colorService = colorService;
		this.brandService = brandService;
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest){
		
		checkCarIdExists(updateCarRequest.getCarId());
		checkColorExists(updateCarRequest.getColorId());
		checkBrandExists(updateCarRequest.getBrandId());
		
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carDao.save(car);
		
		return new SuccessDataResult<UpdateCarRequest>(updateCarRequest,
			Messages.CARUPDATED + car.getCarName());
	}

	@Override
	@Transactional
	public Result create(CreateCarRequest createCarRequest){
		
		checkColorExists(createCarRequest.getColorId());
		checkBrandExists(createCarRequest.getBrandId());
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		
		return new SuccessDataResult<CreateCarRequest>(createCarRequest,
			Messages.CARADDED + car.getCarName());
	}

	@Override
	public DataResult<List<ListCarDto>> listAll(){
		
		Sort sort = Sort.by(Direction.ASC, "carId");
		List<Car> cars = this.carDao.findAll(sort);
		List<ListCarDto> listCarDtos = cars.stream()
			.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(listCarDtos, Messages.CARSLISTED);
	}

	@Override
	public DataResult<CarDto> getById(int carId){
		
		checkCarIdExists(carId);
		
		Car car = this.carDao.getById(carId);
		CarDto carDto = this.modelMapperService.forDto().map(car, CarDto.class);
		
		return new SuccessDataResult<CarDto>(carDto, Messages.CARGETTEDBYID);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction){
		
		Sort sort = Sort.by(direction, "carName");
		List<Car> cars = this.carDao.findAll(sort);
		List<ListCarDto> listCarDtos = cars.stream()
			.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(listCarDtos,
				Messages.DATALISTEDIN + direction.toString()  + Messages.ORDER);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize){
		
		checkPageNoAndPageSize(pageNo, pageSize);
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Car> cars = this.carDao.findAll(pageable).getContent();
		List<ListCarDto> listCarDtos = cars.stream()
			.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(listCarDtos,
			Messages.DATAINPAGE + Integer.toString(pageNo) + Messages.ISLISTEDWITHDATASIZE
			+ Integer.toString(pageSize));
	}

	@Override
	public DataResult<List<ListCarDto>> findByDailyPriceLessThanEqual(double dailyPrice){
		
		List<Car> cars = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
		List<ListCarDto> listCarDtos = cars.stream()
			.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(listCarDtos,
			Messages.CARLISTEDRESPECTTODAILYPRICE + Double.toString(dailyPrice));
	}

	@Override
	public Result delete(int carId){
		
		checkCarIdExists(carId);
		
		String carNameBeforeDelete = this.carDao.findByCarId(carId).getCarName();
		this.carDao.deleteById(carId);
		
		return new SuccessResult(Messages.CARDELETED + carNameBeforeDelete);
	}
	
	@Override
	public void updateKilometer(Car car) {
		
		checkCarIdExists(car.getCarId());
		
		this.carDao.save(car);
		
	}

	private void checkCarIdExists(int carId){
		
		if (!this.carDao.existsById(carId)) {
			
			throw new BusinessException(Messages.CARNOTFOUND);
		}
	}
	
	private void checkPageNoAndPageSize(int pageNo, int pageSize) {
		
		if(pageNo <= 0) {
			
			throw new BusinessException(Messages.PAGENOCANNOTLESSTHANZERO);
		}else if(pageSize <= 0) {
			
			throw new BusinessException(Messages.PAGESIZECANNOTLESSTHANZERO);
		}
	}
	
	private void checkColorExists(int colorId) {
		
		if(!this.colorService.getById(colorId).isSuccess()) {
			
			throw new BusinessException(Messages.COLORNOTFOUND);
		}
	}
	
	private void checkBrandExists(int brandId) {
		
		if(!this.brandService.getById(brandId).isSuccess()) {
			
			throw new BusinessException(Messages.BRANDNOTFOUND);
		}
	}
}