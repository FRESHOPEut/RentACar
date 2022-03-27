package com.turkcell.rentACar.core.utilities.posServiceAdapter.abstracts;

import com.turkcell.rentACar.entities.concretes.CreditCard;

public interface PosService {

	boolean checkCardIsActive(CreditCard creditCard);
}
