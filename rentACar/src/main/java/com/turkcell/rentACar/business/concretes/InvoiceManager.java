package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.abstracts.RentalService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceDto;
import com.turkcell.rentACar.business.dtos.invoice.ListInvoiceDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentDto;
import com.turkcell.rentACar.business.dtos.rental.RentalDto;
import com.turkcell.rentACar.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.abstracts.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACar.entities.concretes.Invoice;
import com.turkcell.rentACar.entities.concretes.Payment;
import com.turkcell.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService{
	
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	private PaymentService paymentService;
	private CustomerService customerService;
	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService,
			RentalService rentalService, PaymentService paymentService, CustomerService customerService) {
		
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
		this.paymentService = paymentService;
		this.customerService = customerService;
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		
		checkInvoiceIdExists(updateInvoiceRequest.getInvoiceId());
		checkRentalExists(updateInvoiceRequest.getRentalId());
		checkPaymentExists(updateInvoiceRequest.getPaymentId());
		
		RentalDto rentalDto = this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData();
		PaymentDto paymentDto = this.paymentService.getByPaymentId(updateInvoiceRequest.getPaymentId()).getData();
		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		invoice = updatingInvoice(rentalDto, paymentDto, invoice);
		this.invoiceDao.save(invoice);
		
		return new SuccessDataResult<UpdateInvoiceRequest>(updateInvoiceRequest, Messages.INVOICEUPDATED
			+ updateInvoiceRequest.getRentalId());
	}

	@Override
	@Transactional
	public Result create(CreateInvoiceRequest createInvoiceRequest) {
		
		checkRentalExists(createInvoiceRequest.getRentalId());
		checkPaymentExists(createInvoiceRequest.getPaymentId());
		
		RentalDto rentalDto = this.rentalService.getById(createInvoiceRequest.getRentalId()).getData();
		PaymentDto paymentDto = this.paymentService.getByPaymentId(createInvoiceRequest.getPaymentId())
				.getData();
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice = creatingInvoice(rentalDto, paymentDto,  invoice);
		this.invoiceDao.save(invoice);
		
		return new SuccessDataResult<CreateInvoiceRequest>(createInvoiceRequest, Messages.INVOICEADDED
			+ createInvoiceRequest.getRentalId());
	}

	@Override
	public DataResult<List<ListInvoiceDto>> listAll() {
		
		Sort sort = Sort.by(Direction.ASC, "invoiceId");
		List<Invoice> invoices = this.invoiceDao.findAll(sort);
		List<ListInvoiceDto> listInvoiceDtos = invoices.stream()
			.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(listInvoiceDtos, Messages.INVOICESLISTED);
	}

	@Override
	public DataResult<InvoiceDto> getByInvoiceId(int invoiceId) {
		
		checkInvoiceIdExists(invoiceId);

		Invoice invoice = this.invoiceDao.getByInvoiceId(invoiceId);
		InvoiceDto invoiceDto = this.modelMapperService.forDto().map(invoice, InvoiceDto.class);
		
		return new SuccessDataResult<InvoiceDto>(invoiceDto, Messages.INVOICEGETTEDBYID);
	}
	
	@Override
	public DataResult<InvoiceDto> getByInvoiceNumber(String invoiceNumber){
		
		checkInvoiceNumberExists(invoiceNumber);
		
		Invoice invoice = this.invoiceDao.getByInvoiceNumber(invoiceNumber);
		InvoiceDto invoiceDto = this.modelMapperService.forDto().map(invoice, InvoiceDto.class);
		
		return new SuccessDataResult<InvoiceDto>(invoiceDto, Messages.INVOICEGETTEDBYNUMBER);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAllPaged(int pageNo, int pageSize) {
		
		checkPageNoAndPageSize(pageNo, pageSize);
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Invoice> invoices = this.invoiceDao.findAll(pageable).getContent();
		List<ListInvoiceDto> listInvoiceDtos = invoices.stream()
			.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(listInvoiceDtos, Messages.DATAINPAGE
			+ Integer.toString(pageNo) + Messages.ISLISTEDWITHDATASIZE + Integer.toString(pageSize));
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateofBetween(LocalDate startDate, LocalDate finishDate) {
		
		List<Invoice> invoices = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> listInvoiceDtos = invoices.stream()
			.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(listInvoiceDtos, Messages.DATABETWEEN
			+ startDate.toString() + Messages.AND + finishDate.toString() + Messages.ISLISTED);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomerId(int customerId) {
		
		checkCustomerIdExists(customerId);
		
		List<Invoice> invoices = this.invoiceDao.findByInvoiceCustomer_UserId(customerId);
		List<ListInvoiceDto> listInvoiceDtos = invoices.stream()
			.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(listInvoiceDtos,
			Messages.INVOICESGETTEDBYCUSTOMERID + Integer.toString(customerId));
	}

	@Override
	public Result delete(int invoiceId) {
		
		checkInvoiceIdExists(invoiceId);
		
		this.invoiceDao.deleteById(invoiceId);
		
		return new SuccessResult(Messages.INVOICEDELETED);
	}
	
	private void checkInvoiceIdExists(int invoiceId) {
		
		if(!this.invoiceDao.existsByInvoiceId(invoiceId)) {
			
			throw new BusinessException(Messages.INVOICENOTFOUND);
		}
	}
	
	private void checkInvoiceNumberExists(String invoiceNumber) {
		
		if(!this.invoiceDao.existsByInvoiceNumber(invoiceNumber)) {
			
			throw new BusinessException(Messages.INVOICENOTFOUNDBYNUMBER);
		}
	}
	
	private void checkCustomerIdExists(int customerId) {
		
		if(!this.invoiceDao.existsByInvoiceCustomer_UserId(customerId)) {
			
			throw new BusinessException(Messages.INVOICECUSTOMERRENTNOTFOUND);
		}
	}

	private Invoice creatingInvoice(RentalDto rentalDto, PaymentDto paymentDto, Invoice invoice) {
		
		LocalDate date = LocalDate.now();
		
		Payment payment = this.modelMapperService.forDto().map(paymentDto, Payment.class);
		Rental rental = this.modelMapperService.forDto().map(rentalDto, Rental.class);
		
		invoice.setCreateDate(date);
		invoice.setRentDate(rental.getRentalDate());
		invoice.setReturnDate(rental.getReturnDate());
		invoice.setRentedDays(rentedDayCalculator(rental));
		invoice.setRentTotalPrice(totalPriceCalculator(rental, invoice.getRentedDays()));
		invoice.setInvoiceCustomer(this.customerService.setByCustomerId(rentalDto.getCustomerId()));
		invoice.setInvoicePayment(payment);
		invoice.setInvoiceNumber(UUID.randomUUID().toString());
		
		return invoice;
	}
	
	private Invoice updatingInvoice(RentalDto rentalDto, PaymentDto paymentDto, Invoice invoice) {
		
		LocalDate date = LocalDate.now();
		
		Payment payment = this.modelMapperService.forDto().map(paymentDto, Payment.class);
		Rental rental = this.modelMapperService.forDto().map(rentalDto, Rental.class);
		
		invoice.setCreateDate(date);
		invoice.setRentDate(rental.getRentalDate());
		invoice.setReturnDate(rental.getReturnDate());
		invoice.setRentedDays(rentedDayCalculator(rental));
		invoice.setRentTotalPrice(totalPriceCalculator(rental, invoice.getRentedDays()));
		invoice.setInvoiceCustomer(this.customerService.setByCustomerId(rentalDto.getCustomerId()));
		invoice.setInvoicePayment(payment);
		invoice.setInvoiceNumber(UUID.randomUUID().toString());
		
		return invoice;
	}
	
	private int rentedDayCalculator(Rental rental) {
		
		int passedDays = 1;
		
		if(ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate()) == 0) {
			
			return passedDays;
		}
		
		passedDays = Integer.valueOf((int) ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate()));
		
		return passedDays;
	}
	
	private double totalPriceCalculator(Rental rental, int passedDays) {
		
		double totalPrice = rental.getRentalTotalDailyPrice() * passedDays;
		
		if(rental.getCurrentCity() != rental.getReturnCity()) {
			
			totalPrice += 750;
		}
		
		return totalPrice;
	}
	
	private void checkPageNoAndPageSize(int pageNo, int pageSize) {
		
		if(pageNo <= 0) {
			
			throw new BusinessException(Messages.PAGENOCANNOTLESSTHANZERO);
		}else if(pageSize <= 0) {
			
			throw new BusinessException(Messages.PAGESIZECANNOTLESSTHANZERO);
		}
	}
	
	private void checkRentalExists(int rentalId) {
		
		if(!this.rentalService.getById(rentalId).isSuccess()) {
			
			throw new BusinessException(Messages.RENTALNOTFOUND);
		}
	}
	
	private void checkPaymentExists(int paymentId) {
		
		if(!this.paymentService.getByPaymentId(paymentId).isSuccess()) {
			
			throw new BusinessException(Messages.PAYMENTNOTFOUND);
		}
	}
}