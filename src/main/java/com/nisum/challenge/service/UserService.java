package com.nisum.challenge.service;

import com.nisum.challenge.dto.UserRequestDto;
import com.nisum.challenge.dto.UserRequestUpdateDto;
import com.nisum.challenge.dto.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponseDto> findAll();

    UserResponseDto findById(UUID id);

    UserResponseDto create(UserRequestDto userRequestDto);

    UserResponseDto update(UUID id, UserRequestUpdateDto userRequestUpdateDto);

    void delete(UUID id);

}
