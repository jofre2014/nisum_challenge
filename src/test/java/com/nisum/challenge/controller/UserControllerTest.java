package com.nisum.challenge.controller;

import com.nisum.challenge.dto.UserRequestDto;
import com.nisum.challenge.dto.UserRequestUpdateDto;
import com.nisum.challenge.dto.UserResponseDto;
import com.nisum.challenge.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        UserResponseDto userResponseDto = new UserResponseDto();
        when(userService.findAll()).thenReturn(Collections.singletonList(userResponseDto));

        ResponseEntity<List<UserResponseDto>> response = userController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testFindById() {
        UserResponseDto userResponseDto = new UserResponseDto();
        UUID id = UUID.randomUUID();
        when(userService.findById(id)).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userController.findById(id.toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseDto, response.getBody());
    }

    @Test
    void testCreate() {
        UserRequestDto userRequestDto = new UserRequestDto();
        UserResponseDto userResponseDto = new UserResponseDto();
        when(userService.create(any(UserRequestDto.class))).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userController.create(userRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResponseDto, response.getBody());
    }

    @Test
    void testUpdate() {
        UserRequestUpdateDto userRequestUpdateDto = new UserRequestUpdateDto();
        UserResponseDto userResponseDto = new UserResponseDto();
        UUID id = UUID.randomUUID();
        when(userService.update(any(UUID.class), any(UserRequestUpdateDto.class))).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userController.update(id.toString(), userRequestUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseDto, response.getBody());
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = userController.deleteById(id.toString());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

