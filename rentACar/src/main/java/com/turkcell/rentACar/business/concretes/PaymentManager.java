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

import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.business.dtos.payment.ListPaymentDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentDto;
import com.turkcell.rentACar.business.dtos.rental.RentalDto;
import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.create.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.create.CreatePaymentWithSavedCardRequest;
import com.turkcell.rentACar.business.requests.update.UpdatePaymentRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.abstracts.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concretes.CreditCard;
import com.turkcell.rentACar.entities.concretes.Payment;
import com.turkcell.rentACar.entities.concretes.Rental;

@Service
public class PaymentManager implements PaymentService{
	
	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private CreditCardService creditCardService;
	private RentalService rentalService;
	private CustomerService customerService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,
			CreditCardService creditCardService, RentalService rentalService, CustomerService customerService) {

		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.creditCardService = creditCardService;
		this.rentalService = rentalService;
		this.customerService = customerService;
	}

	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) {
		
		checkPaymentIdExists(updatePaymentRequest.getPaymentId());
		isRentalExists(updatePaymentRequest.getRentalId());
		
		Payment payment = this.modelMapperService.forRequest().map(updatePaymentRequest, Payment.class);		
		this.paymentDao.save(payment);
		
		return new SuccessDataResult<UpdatePaymentRequest>(updatePaymentRequest, Messages.PAYMENTUPDATED);
	}

	@Override
	@Transactional
	public Result create(CreatePaymentRequest createPaymentRequest, boolean saveCard) {
		
		isRentalExists(createPaymentRequest.getRentalId());
		CreditCard creditCard = this.modelMapperService.forRequest().map(createPaymentRequest.getCreditCard(), CreditCard.class);
		creditCard = setCustomerToCreditCard(creditCard, createPaymentRequest.getRentalId());
		
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		checkCreditCardIsSaved(saveCard, payment, creditCard);
		
		return new SuccessDataResult<CreatePaymentRequest>(createPaymentRequest, Messages.PAYMENTADDED);
	}
	
	@Override
	public Result createWithSavedCard(CreatePaymentWithSavedCardRequest createPaymentWithSavedCardRequest) {
		
		checkCreditCardExists(createPaymentWithSavedCardRequest.getCreditCardId());
		isRentalExists(createPaymentWithSavedCardRequest.getRentalId());
		
		Payment payment = this.modelMapperService.forRequest().map(createPaymentWithSavedCardRequest, Payment.class);
		this.paymentDao.save(payment);
		
		return new SuccessDataResult<CreatePaymentWithSavedCardRequest>(createPaymentWithSavedCardRequest, Messages.PAYMENTADDED);
	}

	@Override
	public DataResult<List<ListPaymentDto>> listAll() {
		
		Sort sort = Sort.by(Direction.ASC, "paymentId");
		List<Payment> payments = this.paymentDao.findAll(sort);
		List<ListPaymentDto> listPaymentDtos = payments.stream()
			.map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListPaymentDto>>(listPaymentDtos, Messages.PAYMENTSLISTED);
	}

	@Override
	public DataResult<PaymentDto> getByPaymentId(int paymentId) {
		
		checkPaymentIdExists(paymentId);
		
		Payment payment = this.paymentDao.getByPaymentId(paymentId);
		PaymentDto paymentDto = this.modelMapperService.forDto().map(payment, PaymentDto.class);
		
		return new SuccessDataResult<PaymentDto>(paymentDto, Messages.PAYMENTGETTEDBYID);
	}

	@Override
	public DataResult<List<ListPaymentDto>> getAllSorted(Sort.Direction direction) {
		
		Sort sort = Sort.by(direction, "paymentId");
		List<Payment> payments = this.paymentDao.findAll(sort);
		List<ListPaymentDto> listPaymentDtos = payments.stream()
			.map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListPaymentDto>>(listPaymentDtos,
				Messages.DATALISTEDIN + direction.toString() + Messages.ORDER);
	}

	@Override
	public DataResult<List<ListPaymentDto>> getAllPaged(int pageNo, int pageSize) {
		
		checkPageNoAndPageSize(pageNo, pageSize);
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Payment> payments = this.paymentDao.findAll(pageable).getContent();
		List<ListPaymentDto> listPaymentDtos = payments.stream()
			.map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListPaymentDto>>(listPaymentDtos, Messages.DATAINPAGE
			+ Integer.toString(pageNo) + Messages.ISLISTEDWITHDATASIZE + Integer.toString(pageSize));
	}

	@Override
	public Result delete(int paymentId) {
		
		checkPaymentIdExists(paymentId);
		
		this.paymentDao.deleteById(paymentId);
		
		return new SuccessResult(Messages.PAYMENTDELETED);
	}
	
	private void checkPaymentIdExists(int paymentId) {
		
		if(!this.paymentDao.existsByPaymentId(paymentId)) {
			
			throw new BusinessException(Messages.PAYMENTNOTFOUND);
		}
	}
	
	@Transactional
	private void saveCreditCard(CreateCreditCardRequest createCreditCardRequest, boolean saveCard) {
		
		if(saveCard) {
			this.creditCardService.create(createCreditCardRequest);
		}

	}
	
	private void checkCreditCardExists(int creditCardId) {
		
		if(!this.creditCardService.getById(creditCardId).isSuccess()) {
			
			throw new BusinessException(Messages.CREDITCARDNOTFOUND);
		}
	}
	
	private void isRentalExists(int rentalId) {
		
		if(!this.rentalService.getById(rentalId).isSuccess()) {
			
			throw new BusinessException(Messages.RENTALNOTFOUND);
		}
	}
	
	private void checkPageNoAndPageSize(int pageNo, int pageSize) {
		
		if(pageNo <= 0) {
			
			throw new BusinessException(Messages.PAGENOCANNOTLESSTHANZERO);
		}else if(pageSize <= 0) {
			
			throw new BusinessException(Messages.PAGESIZECANNOTLESSTHANZERO);
		}
	}
	
	@Transactional
	private void checkCreditCardIsSaved(boolean saveCard, Payment payment, CreditCard creditCard) {
		
		if(saveCard) {
			
			payment.setPaymentCard(creditCard);
			
		}
		
		if(saveCard == false) {
			
			payment.setPaymentCard(null);
		}
		
		this.paymentDao.save(payment);
	}
	
	private CreditCard setCustomerToCreditCard(CreditCard creditCard, int rentalId) {
		
		RentalDto rentalDto = this.rentalService.getById(rentalId).getData();
		//Rental rental = this.modelMapperService.forDto().map(rentalDto, Rental.class);
		creditCard.setCreditCardCustomer(this.customerService.setByCustomerId(rentalDto.getCustomerId()));
		
		return creditCard;
	}
}