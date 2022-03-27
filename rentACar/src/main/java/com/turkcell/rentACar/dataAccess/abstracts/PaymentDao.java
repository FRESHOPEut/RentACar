package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Payment;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer>{

	boolean existsByPaymentId(int paymentId);
	
	Payment getByPaymentId(int paymentId);
	
	List<Payment> getByPaymentRental_RentalId(int rentalId);
	
	Payment getByPaymentInvoice_InvoiceId(int invoiceId);
}
