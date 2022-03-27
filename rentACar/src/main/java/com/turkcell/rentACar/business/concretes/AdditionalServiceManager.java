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

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.business.dtos.additionalService.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.additionalService.ListAdditionalServiceDto;
import com.turkcell.rentACar.business.requests.create.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.abstracts.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao,
			ModelMapperService modelMapperService) {
		
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest){
		
		checkAdditionalServiceId(updateAdditionalServiceRequest.getAdditionalServiceId());
		
		AdditionalService additionalService = this.modelMapperService.forRequest()
			.map(updateAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessDataResult<UpdateAdditionalServiceRequest>(updateAdditionalServiceRequest,
			Messages.ADDITIONALSERVICEUPDATED + additionalService.getAdditionalServiceName());
	}

	@Override
	@Transactional
	public Result create(CreateAdditionalServiceRequest createAdditionalServiceRequest){
		
		checkAdditionalServiceName(createAdditionalServiceRequest.getAdditionalServiceName());
		
		AdditionalService additionalService = this.modelMapperService.forRequest()
			.map(createAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessDataResult<CreateAdditionalServiceRequest>(createAdditionalServiceRequest,
			Messages.ADDITIONALSERVICEADDED + additionalService.getAdditionalServiceName());
	}

	@Override
	public DataResult<List<ListAdditionalServiceDto>> listAll(){
		
		List<AdditionalService> additionalServices = this.additionalServiceDao.findAll();		
		List<ListAdditionalServiceDto> listAdditionalServiceDtos = additionalServices.stream()
			.map(additionalService -> this.modelMapperService.forDto()
			.map(additionalService, ListAdditionalServiceDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListAdditionalServiceDto>>(listAdditionalServiceDtos,
			Messages.ADDITIONALSERVICESLISTED);
	}

	@Override
	public DataResult<AdditionalServiceDto> getById(int additionalServiceId){
		
		checkAdditionalServiceId(additionalServiceId);
		
		AdditionalService additionalService = this.additionalServiceDao.getById(additionalServiceId);
		AdditionalServiceDto additionalServiceDto = this.modelMapperService.forDto()
			.map(additionalService, AdditionalServiceDto.class);
		
		return new SuccessDataResult<AdditionalServiceDto>(additionalServiceDto,
			Messages.ADDITIONALSERVICEGETTEDBYID);
	}

	@Override
	public DataResult<List<ListAdditionalServiceDto>> getAllSorted(Direction direction){
		
		Sort sort = Sort.by(direction, "additionalServiceName");
		List<AdditionalService> additionalServices = this.additionalServiceDao.findAll(sort);
		List<ListAdditionalServiceDto> listAdditionalServiceDtos = additionalServices.stream()
			.map(additionalService -> this.modelMapperService.forDto()
			.map(additionalService, ListAdditionalServiceDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListAdditionalServiceDto>>(listAdditionalServiceDtos,
			Messages.DATALISTEDIN + direction.toString() + Messages.ORDER);
	}

	@Override
	public DataResult<List<ListAdditionalServiceDto>> getAllPaged(int pageNo, int pageSize){
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<AdditionalService> additionalServices = this.additionalServiceDao.findAll(pageable).getContent();
		List<ListAdditionalServiceDto> listAdditionalServiceDtos = additionalServices.stream()
			.map(additionalService -> this.modelMapperService.forDto()
			.map(additionalService, ListAdditionalServiceDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListAdditionalServiceDto>>(listAdditionalServiceDtos,
			Messages.DATAINPAGE + Integer.toString(pageNo) + Messages.ISLISTEDWITHDATASIZE
			+ Integer.toString(pageSize));
	}

	@Override
	public Result delete(int additionalServiceId){
		
		checkAdditionalServiceId(additionalServiceId);
	
		String additionalServiceNameBefore = this.additionalServiceDao
			.findByAdditionalServiceId(additionalServiceId).getAdditionalServiceName();
		this.additionalServiceDao.deleteById(additionalServiceId);
		
		return new SuccessResult(Messages.ADDITIONALSERVICEDELETED + additionalServiceNameBefore);
	}
	
	@Override
	public List<AdditionalService> getByRentalId(int rentalId){
		
		List<AdditionalService> additionalServices = this.additionalServiceDao
			.findAllByAdditionalServiceRental_RentalId(rentalId);
		
		return additionalServices;
	}

	private void checkAdditionalServiceName(String additionalServiceName){
		
		if (this.additionalServiceDao.existsByAdditionalServiceName(additionalServiceName)) {
			
			throw new BusinessException(Messages.ADDITIONALSERVICEEXISTS + additionalServiceName);
		}
	}

	private void checkAdditionalServiceId(int additionalServiceId){
		
		if (!this.additionalServiceDao.existsById(additionalServiceId)) {
			
			throw new BusinessException(Messages.ADDITIONALSERVICENOTFOUND);
		}
	}
}