package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto foodDto) {

        ModelMapper modelMapper = new ModelMapper();
        FoodEntity foodEntity = modelMapper.map(foodDto, FoodEntity.class);

        String publicFoodId = String.valueOf(new SecureRandom());
        foodEntity.setFoodId(publicFoodId);

        FoodEntity storedFood = foodRepository.save(foodEntity);

        FoodDto foodDto2 = new FoodDto();
        foodDto2 = modelMapper.map(storedFood, FoodDto.class);

        return foodDto2;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {

        FoodDto returnValue = new FoodDto();
        ModelMapper modelMapper = new ModelMapper();

        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);

        if (foodEntity == null) {
            throw new Exception(foodId);
        }

        returnValue = modelMapper.map(foodEntity, FoodDto.class);

        return returnValue;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {

        FoodDto returnValue = new FoodDto();
        ModelMapper modelMapper = new ModelMapper();

        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);

        if (foodEntity == null) {
            throw new Exception(foodId);
        }

        foodEntity.setFoodCategory(foodDetails.getFoodCategory());
        foodEntity.setFoodName(foodDetails.getFoodName());
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());

        FoodEntity updatedFoodEntity = foodRepository.save(foodEntity);
        returnValue = modelMapper.map(updatedFoodEntity, FoodDto.class);

        return returnValue;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {

        FoodEntity foodEntity = foodRepository.findByFoodId(id);

        if (foodEntity == null) {
            throw new Exception(id);
        }

        foodRepository.delete(foodEntity);
    }

    @Override
    public List<FoodDto> getFoods() {

        List<FoodDto> returnValue = new ArrayList<>();
        Iterable<FoodEntity> iterableObjects = foodRepository.findAll();

        for (FoodEntity foodEntity : iterableObjects) {
            FoodDto foodDto = new FoodDto();
            BeanUtils.copyProperties(foodEntity, foodDto);
            returnValue.add(foodDto);
        }

        return returnValue;
    }
}
