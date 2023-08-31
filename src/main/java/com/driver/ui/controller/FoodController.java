package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	private FoodService foodService;

	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{

		FoodDto foodDto = foodService.getFoodById(id);
		FoodDetailsResponse returnValue = new FoodDetailsResponse();
		BeanUtils.copyProperties(foodDto, returnValue);
		return returnValue;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {

		FoodDto foodDto = new FoodDto();
		BeanUtils.copyProperties(foodDetails, foodDto);
		FoodDto createdFood = foodService.createFood(foodDto);
		FoodDetailsResponse returnValue = new FoodDetailsResponse();
		BeanUtils.copyProperties(createdFood, returnValue);
		return returnValue;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{

		FoodDto foodDto = new FoodDto();
		BeanUtils.copyProperties(foodDetails, foodDto);
		FoodDto updatedFood = foodService.updateFoodDetails(id, foodDto);
		FoodDetailsResponse returnValue = new FoodDetailsResponse();
		BeanUtils.copyProperties(updatedFood, returnValue);
		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{

		foodService.deleteFoodItem(id);
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {

		List<FoodDto> foodDtos = foodService.getFoods();
		List<FoodDetailsResponse> returnValue = new ArrayList<>();
		for (FoodDto foodDto : foodDtos) {
			FoodDetailsResponse response = new FoodDetailsResponse();
			BeanUtils.copyProperties(foodDto, response);
			returnValue.add(response);
		}
		return returnValue;
	}
}
