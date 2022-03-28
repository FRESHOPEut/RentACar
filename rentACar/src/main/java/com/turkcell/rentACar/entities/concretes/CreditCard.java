package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_cards")
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "credit_card_id")
	private int creditCardId;
	
	@ManyToOne()
	private Customer creditCardCustomer;
	
	@Column(name = "credit_card_number")
	private String creditCardNumber;
	
	@Column(name = "credit_card_cvv")
	private String creditCardCvv;
	
	@Column(name = "credit_card_owner_name")
	private String creditCardOwnerName;
	
	@Column(name = "credit_card_expiration_date")
	private LocalDate creditCardExpirationDate;
	
	@OneToMany(mappedBy = "paymentCard")
	private List<Payment> cardPayments;
}
