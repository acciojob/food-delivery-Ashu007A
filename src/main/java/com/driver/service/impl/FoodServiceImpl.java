package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto foodDto) {
        FoodEntity foodEntity = new FoodEntity();
        BeanUtils.copyProperties(foodDto, foodEntity);

        FoodEntity savedFood = foodRepository.save(foodEntity);
        FoodDto savedFoodDto = new FoodDto();
        BeanUtils.copyProperties(savedFood, savedFoodDto);

        return savedFoodDto;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        if (foodEntity == null) {
            throw new Exception("Food not found");
        }
        FoodDto foodDto = new FoodDto();
        BeanUtils.copyProperties(foodEntity, foodDto);

        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        if (foodEntity == null) {
            throw new Exception("Food not found");
        }

        // Update the properties of foodEntity using foodDetails

        FoodEntity updatedFoodEntity = foodRepository.save(foodEntity);
        FoodDto updatedFoodDto = new FoodDto();
        BeanUtils.copyProperties(updatedFoodEntity, updatedFoodDto);

        return updatedFoodDto;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(id);
        if (foodEntity == null) {
            throw new Exception("Food not found");
        }
        foodRepository.delete(foodEntity);
    }

    @Override
    public List<FoodDto> getFoods() {
        Iterable<FoodEntity> foodEntities = foodRepository.findAll();
        List<FoodDto> foodDtos = new ArrayList<>();

        for (FoodEntity entity : foodEntities) {
            FoodDto foodDto = new FoodDto();
            BeanUtils.copyProperties(entity, foodDto);
            foodDtos.add(foodDto);
        }

        return foodDtos;
    }
}
