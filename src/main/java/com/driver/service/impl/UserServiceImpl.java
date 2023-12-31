package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) throws Exception {

        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new Exception("Record already exists!");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        String publicUserId = String.valueOf(new SecureRandom());
        userEntity.setUserId(publicUserId);

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) throws Exception {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new Exception(email);
        }

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {

        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new Exception(userId);
        }
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) throws Exception {

        UserDto returnValue = new UserDto();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new Exception(userId);
        }

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new Exception(userId);
        }

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers() {

        List<UserDto> returnValue = new ArrayList<>();

        Iterable<UserEntity> iterableObjects = userRepository.findAll();

        for (UserEntity userEntity : iterableObjects) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }
}
