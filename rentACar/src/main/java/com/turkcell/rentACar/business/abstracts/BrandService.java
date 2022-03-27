package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.brand.BrandDto;
import com.turkcell.rentACar.business.dtos.brand.ListBrandDto;
import com.turkcell.rentACar.business.requests.create.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.update.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface BrandService {

	Result update(UpdateBrandRequest updateBrandRequest);

	Result create(CreateBrandRequest createBrandRequest);

	DataResult<List<ListBrandDto>> listAll();

	DataResult<BrandDto> getById(int brandId);
	
	DataResult<List<ListBrandDto>> getAllSorted(Sort.Direction direction);
	
	DataResult<List<ListBrandDto>> getAllPaged(int pageNo, int pageSize);

	Result delete(int brandId);
	
}
