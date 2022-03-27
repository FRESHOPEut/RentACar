package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.constants.Messages;
import com.turkcell.rentACar.business.dtos.color.ColorDto;
import com.turkcell.rentACar.business.dtos.color.ListColorDto;
import com.turkcell.rentACar.business.requests.create.CreateColorRequest;
import com.turkcell.rentACar.business.requests.update.UpdateColorRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.abstracts.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest){
		
		checkColorId(updateColorRequest.getColorId());
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		this.colorDao.save(color);
		
		return new SuccessDataResult<UpdateColorRequest>(updateColorRequest,
			Messages.COLORUPDATED + color.getColorName());
	}

	@Override
	@Transactional
	public Result create(CreateColorRequest createColorRequest){
		
		checkColorName(createColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		this.colorDao.save(color);
		
		return new SuccessDataResult<CreateColorRequest>(createColorRequest,
			Messages.COLORADDED + color.getColorName());
	}

	@Override
	public DataResult<List<ListColorDto>> listAll(){
		
		List<Color> colors = this.colorDao.findAll();
		List<ListColorDto> listColorDtos = colors.stream()
			.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(listColorDtos, Messages.COLORSLISTED);
	}

	@Override
	public DataResult<ColorDto> getById(int colorId){
		
		checkColorId(colorId);
		
		Color color = this.colorDao.getById(colorId);
		ColorDto colorDto = this.modelMapperService.forDto().map(color, ColorDto.class);
		
		return new SuccessDataResult<ColorDto>(colorDto, Messages.COLORGETTEDBYID);
	}

	@Override
	public DataResult<List<ListColorDto>> getAllSorted(Sort.Direction direction){
		
		Sort sort = Sort.by(direction, "colorName");
		List<Color> colors = this.colorDao.findAll(sort);
		List<ListColorDto> listColorDtos = colors.stream()
			.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(listColorDtos,
			Messages.DATALISTEDIN + direction.toString() + Messages.ORDER);
	}

	@Override
	public DataResult<List<ListColorDto>> getAllPaged(int pageNo, int pageSize){
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Color> colors = this.colorDao.findAll(pageable).getContent();
		List<ListColorDto> listColorDtos = colors.stream()
			.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
			.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(listColorDtos, Messages.DATAINPAGE +
			Integer.toString(pageNo) + Messages.ISLISTEDWITHDATASIZE + Integer.toString(pageSize));
	}

	@Override
	public Result delete(int colorId){
		
		checkColorId(colorId);
		
		String colorNameBeforeDelete = this.colorDao.findByColorId(colorId).getColorName();
		this.colorDao.deleteById(colorId);
		
		return new SuccessResult(Messages.COLORDELETED + colorNameBeforeDelete);
	}

	private void checkColorName(String colorName){
		
		if (this.colorDao.existsByColorName(colorName)) {
			
			throw new BusinessException(Messages.COLOREXISTS + colorName);
		}
	}

	private void checkColorId(int colorId){
		
		if (!this.colorDao.existsById(colorId)) {
			
			throw new BusinessException(Messages.COLORNOTFOUND);
		}
	}
}