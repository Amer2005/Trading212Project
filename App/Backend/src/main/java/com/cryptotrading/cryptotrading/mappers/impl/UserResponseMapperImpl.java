package com.cryptotrading.cryptotrading.mappers.impl;


import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.response.UserResponseDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapperImpl implements Mapper<User, UserResponseDto> {

    private final ModelMapper modelMapper;

    public UserResponseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDto mapTo(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public User mapFrom(UserResponseDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
