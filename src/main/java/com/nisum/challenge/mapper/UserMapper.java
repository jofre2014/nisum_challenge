package com.nisum.challenge.mapper;

import com.nisum.challenge.dto.UserRequestDto;
import com.nisum.challenge.dto.UserResponseDto;
import com.nisum.challenge.entity.User;
import org.mapstruct.Mapper;

@Mapper(uses = PhoneMapper.class)
public interface UserMapper {


    UserResponseDto toDto(User user);
    User toEntity(UserRequestDto userRequestDto);
}
