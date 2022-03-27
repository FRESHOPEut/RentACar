package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.city.CityDto;
import com.turkcell.rentACar.business.dtos.city.ListCityDto;
import com.turkcell.rentACar.core.utilities.results.DataResult;

public interface CityService {

	DataResult<List<ListCityDto>> listAll();

	DataResult<CityDto> getByCityPlate(int cityPlate);

	DataResult<List<ListCityDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<ListCityDto>> getAllPaged(int pageNo, int pageSize);
}
