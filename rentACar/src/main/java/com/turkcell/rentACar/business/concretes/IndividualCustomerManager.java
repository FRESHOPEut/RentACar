package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.individualCustomer.ListIndividualCustomerDto;
import com.turkcell.rentACar.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.abstracts.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService{

	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	
	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService,
			CustomerService customerService) {
		
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest){
		
		checkIndividualCustomerIdExists(updateIndividualCustomerRequest.getUserId());
		this.customerService.checkEmailExists(updateIndividualCustomerRequest.getEmail());
		
		IndividualCustomerDto individualCustomerTemp = getByIndividualCustomerId(updateIndividualCustomerRequest.getUserId()).getData();
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
			.map(updateIndividualCustomerRequest, IndividualCustomer.class);
		individualCustomer.setRegisteredDate(individualCustomerTemp.getRegisteredDate());
		individualCustomer.setNationalIdentity(individualCustomerTemp.getNationalIdentity());
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessDataResult<UpdateIndividualCustomerRequest>(updateIndividualCustomerRequest,
			Messages.INDIVIDUALCUSTOMERUPDATED + individualCustomer.getFirstname());
	}

	@Override
	@Transactional
	public Result create(CreateIndividualCustomerRequest createIndividualCustomerRequest){
		
		LocalDate date = LocalDate.now();
		
		checkIndividualIdentityNoContainsLetter(createIndividualCustomerRequest.getNationalIdentity());
		checkNationalIdentityExists(createIndividualCustomerRequest.getNationalIdentity());
		this.customerService.checkEmailExists(createIndividualCustomerRequest.getEmail());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
			.map(createIndividualCustomerRequest, IndividualCustomer.class);
		individualCustomer.setRegisteredDate(date);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessDataResult<CreateIndividualCustomerRequest>(createIndividualCustomerRequest,
			Messages.INDIVIDUALCUSTOMERADDED + individualCustomer.getFirstname());
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> listAll(){
		
		Sort sort = Sort.by(Direction.ASC, "userId");
		List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll(sort);
		List<ListIndividualCustomerDto> listIndividualCustomerDtos = individualCustomers.stream()
			.map(individualCustomer -> this.modelMapperService.forDto()
			.map(individualCustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListIndividualCustomerDto>>(listIndividualCustomerDtos,
			Messages.INDIVIDUALCUSTOMERSLISTED);
	}

	@Override
	public DataResult<IndividualCustomerDto> getByIndividualCustomerId(int individualCustomerId){
		
		checkIndividualCustomerIdExists(individualCustomerId);
		
		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(individualCustomerId);
		IndividualCustomerDto individualCustomerDto = this.modelMapperService.forDto()
			.map(individualCustomer, IndividualCustomerDto.class);
		
		return new SuccessDataResult<IndividualCustomerDto>(individualCustomerDto,
			Messages.INDIVIDUALCUSTOMERGETTEDBYID);
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAllSorted(Direction direction){
		
		Sort sort = Sort.by(direction, "firstname");
		List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll(sort);
		List<ListIndividualCustomerDto> listIndividualCustomerDtos = individualCustomers.stream()
			.map(individualCustomer -> this.modelMapperService.forDto()
			.map(individualCustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListIndividualCustomerDto>>(listIndividualCustomerDtos,
			Messages.DATALISTEDIN + direction.toString() + Messages.ORDER);
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAllPaged(int pageNo, int pageSize){
		
		checkPageNoAndPageSize(pageNo, pageSize);
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll(pageable).getContent();
		List<ListIndividualCustomerDto> listIndividualCustomerDtos = individualCustomers.stream()
			.map(individualCustomer -> this.modelMapperService.forDto()
			.map(individualCustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListIndividualCustomerDto>>(listIndividualCustomerDtos,
			Messages.DATAINPAGE + Integer.toString(pageNo) + Messages.ISLISTEDWITHDATASIZE
			+ Integer.toString(pageSize));
	}

	@Override
	public Result delete(int individualCustomerId){
		
		checkIndividualCustomerIdExists(individualCustomerId);
		
		String individualCustomerNameBefore = this.individualCustomerDao.findById(individualCustomerId)
			.get().getFirstname();
		this.individualCustomerDao.deleteById(individualCustomerId);
		
		return new SuccessResult(Messages.INDIVIDUALCUSTOMERDELETED + individualCustomerNameBefore);
	}
	
	private void checkNationalIdentityExists(String nationalIdentity){
		
		if(this.individualCustomerDao.existsByNationalIdentity(nationalIdentity)) {
			
			throw new BusinessException(Messages.INDIVIDUALCUSTOMERIDENTITYEXISTS + nationalIdentity);
		}
	}
	
	private void checkIndividualCustomerIdExists(int individualCustomerId){
		
		if(!this.individualCustomerDao.existsById(individualCustomerId)) {
			
			throw new BusinessException(Messages.INDIVIDUALCUSTOMERNOTFOUND);
		}
	}
	
	private void checkPageNoAndPageSize(int pageNo, int pageSize) {
		
		if(pageNo <= 0) {
			
			throw new BusinessException(Messages.PAGENOCANNOTLESSTHANZERO);
		}else if(pageSize <= 0) {
			
			throw new BusinessException(Messages.PAGESIZECANNOTLESSTHANZERO);
		}
	}
	
	private void checkIndividualIdentityNoContainsLetter(String identityNo) {
		
		boolean result = identityNo.matches("[0-9]+");
		
		if(!result) {
			
			throw new BusinessException(Messages.INDIVIDUALCUSTOMERIDENTITYNOCONTAINSLETTER);
		}
	}
}