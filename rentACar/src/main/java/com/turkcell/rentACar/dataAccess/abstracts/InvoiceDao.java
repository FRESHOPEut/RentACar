package com.turkcell.rentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer>{

	Invoice getByInvoiceId(long invoiceId);
	
	boolean existsByInvoiceId(long invoiceId);
	
	List<Invoice> findByInvoiceCustomer_UserId(int customerId);
	
	boolean existsByInvoiceCustomer_UserId(int customerId);
	
	List<Invoice> findByCreateDateBetween(LocalDate startDate, LocalDate finishDate);
	
	Invoice deleteByInvoiceId(long invoiceId);
}
