package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.color.ColorDto;
import com.turkcell.rentACar.business.dtos.color.ListColorDto;
import com.turkcell.rentACar.business.requests.create.CreateColorRequest;
import com.turkcell.rentACar.business.requests.update.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface ColorService {

	Result update(UpdateColorRequest updateColorRequest);

	Result create(CreateColorRequest createColorRequest);

	DataResult<List<ListColorDto>> listAll();

	DataResult<ColorDto> getById(int colorId);

	DataResult<List<ListColorDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<ListColorDto>> getAllPaged(int pageNo, int pageSize);

	Result delete(int colorId);

}
