package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new Exception("User already exists");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        UserEntity savedUser = userRepository.save(userEntity);
        UserDto savedUserDto = new UserDto();
        BeanUtils.copyProperties(savedUser, savedUserDto);

        return savedUserDto;
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new Exception("User not found");
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new Exception("User not found");
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new Exception("User not found");
        }

        // Update userEntity properties
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        // Update other properties as needed

        UserEntity updatedUserEntity = userRepository.save(userEntity);
        UserDto updatedUserDto = new UserDto();
        BeanUtils.copyProperties(updatedUserEntity, updatedUserDto);

        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new Exception("User not found");
        }
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers() {
        Iterable<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (UserEntity entity : userEntities) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(entity, userDto);
            userDtos.add(userDto);
        }

        return userDtos;
    }
}
