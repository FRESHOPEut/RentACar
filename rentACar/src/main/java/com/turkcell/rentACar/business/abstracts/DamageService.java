package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.damage.DamageDto;
import com.turkcell.rentACar.business.dtos.damage.ListDamageDto;
import com.turkcell.rentACar.business.requests.create.CreateDamageRequest;
import com.turkcell.rentACar.business.requests.update.UpdateDamageRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface DamageService {

	Result update(UpdateDamageRequest updateDamageRequest);
	
	Result create(CreateDamageRequest createDamageRequest);
	
	DataResult<List<ListDamageDto>> listAll();
	
	DataResult<DamageDto> getById(int damageId);
	
	DataResult<List<ListDamageDto>> getAllSorted(Sort.Direction direction);
	
	DataResult<List<ListDamageDto>> getAllPaged(int pageNo, int pageSize);
	
	Result delete(int damageId);
}
