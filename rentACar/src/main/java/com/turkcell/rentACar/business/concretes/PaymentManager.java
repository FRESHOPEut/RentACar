package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.business.dtos.payment.ListPaymentDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentDto;
import com.turkcell.rentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.create.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.create.CreatePaymentWithSavedCardRequest;
import com.turkcell.rentACar.business.requests.update.UpdatePaymentRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.abstracts.ModelMapperService;
import com.turkcell.rentACar.core.utilities.posServiceAdapter.abstracts.PosService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concretes.CreditCard;
import com.turkcell.rentACar.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService{
	
	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private PosService posService;
	private CreditCardService creditCardService;
	private RentalService rentalService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,
			PosService posService, CreditCardService creditCardService, RentalService rentalService) {

		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.posService = posService;
		this.creditCardService = creditCardService;
		this.rentalService = rentalService;
	}

	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) {
		
		checkPaymentIdExists(updatePaymentRequest.getPaymentId());
		isRentalExists(updatePaymentRequest.getRentalId());
		
		Payment payment = this.modelMapperService.forRequest().map(updatePaymentRequest, Payment.class);
		
		checkCustomerCanUseCreditCard(payment);
		
		checkPaymentMethodIsValid(payment.getPaymentCard());
		
		this.paymentDao.save(payment);
		
		return new SuccessDataResult<UpdatePaymentRequest>(updatePaymentRequest, Messages.PAYMENTUPDATED);
	}

	@Override
	@Transactional
	public Result create(CreatePaymentRequest createPaymentRequest, boolean saveCard) {
		
		isRentalExists(createPaymentRequest.getRentalId());
		
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		checkPaymentMethodIsValid(payment.getPaymentCard());
		saveCreditCard(createPaymentRequest.getCreditCard(), saveCard);
		this.paymentDao.save(payment);
		
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
		
		List<Payment> payments = this.paymentDao.findAll();
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
	
	private void checkPaymentMethodIsValid(CreditCard creditCard) {
		
		if(!this.posService.checkCardIsActive(creditCard)) {
			
			throw new BusinessException(Messages.PAYMENTMETHODNOTVALID);
		}
	}
	
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
	
	private void checkCustomerCanUseCreditCard(Payment payment) {
		
		if(payment.getPaymentCard().getCreditCardCustomer().getUserId() != payment.getPaymentRental().getRentalCustomer().getUserId()) {
			
			throw new BusinessException(Messages.CUSTOMERANDCARDDOESNOTMATCH);
		}
	}
	
	private void isRentalExists(int rentalId) {
		
		if(!this.rentalService.getById(rentalId).isSuccess()) {
			
			throw new BusinessException(Messages.RENTALNOTFOUND);
		}
	}
}