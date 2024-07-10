package com.nisum.challenge.service;

import com.nisum.challenge.dto.UserRequestDto;
import com.nisum.challenge.dto.UserRequestUpdateDto;
import com.nisum.challenge.dto.UserResponseDto;
import com.nisum.challenge.entity.User;
import com.nisum.challenge.exception.UserEmailAlreadyExistException;
import com.nisum.challenge.exception.UserNotFundException;
import com.nisum.challenge.repository.UserRepository;
import com.nisum.challenge.security.JwtService;
import com.nisum.challenge.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testFindById() {
        User user = new User();
        UUID id = UUID.randomUUID();
        when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.of(user));

        UserResponseDto responseDto = userService.findById(id);

        assertEquals(user.getId(), responseDto.getId());
    }

    @Test
    void testCreate() {
        UserRequestDto userRequestDto = new UserRequestDto();
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(anyString())).thenReturn("token");

        UserResponseDto responseDto = userService.create(userRequestDto);

        assertEquals(user.getId(), responseDto.getId());
    }

    @Test
    void testUpdate() {
        User user = new User();
        UUID id = UUID.randomUUID();
        user.setId(id);
        user.setEmail("jose@rodriguez.com");

        UserRequestUpdateDto userRequestUpdateDto = new UserRequestUpdateDto();
        userRequestUpdateDto.setEmail("juan@rodriguez.com");


        when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto responseDto = userService.update(id, userRequestUpdateDto);

        assertEquals(user.getId(), responseDto.getId());
    }

    @Test
    void testDelete() {
        User user = new User();
        UUID id = UUID.randomUUID();
        when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.of(user));

        userService.delete(id);

        verify(userRepository, times(1)).save(user);
        assertEquals(false, user.getIsActive());
    }

    @Test
    void testCreateEmailAlreadyExist() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("test@example.com");
        when(userRepository.findByEmailAndIsActiveTrue(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(UserEmailAlreadyExistException.class, () -> {
            userService.create(userRequestDto);
        });
    }

    @Test
    void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFundException.class, () -> {
            userService.findById(id);
        });
    }
}

