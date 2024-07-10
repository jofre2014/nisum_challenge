package com.nisum.challenge.service.impl;

import com.nisum.challenge.dto.PhoneRequestDto;
import com.nisum.challenge.dto.UserRequestDto;
import com.nisum.challenge.dto.UserRequestUpdateDto;
import com.nisum.challenge.dto.UserResponseDto;
import com.nisum.challenge.entity.Phone;
import com.nisum.challenge.entity.User;
import com.nisum.challenge.exception.UserEmailAlreadyExistException;
import com.nisum.challenge.exception.UserNotFundException;
import com.nisum.challenge.exception.UserServerException;
import com.nisum.challenge.mapper.PhoneMapper;
import com.nisum.challenge.mapper.UserMapper;
import com.nisum.challenge.repository.UserRepository;
import com.nisum.challenge.security.JwtService;
import com.nisum.challenge.service.UserService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "No se encontró el usuario con ID: ";
    private static final String EMAIL_EXIST = "El correo ya está registrado";

    private final UserRepository userRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final PhoneMapper phoneMapper = Mappers.getMapper(PhoneMapper.class);

    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAllByIsActiveTrue();
        users.forEach(this::updateLastLogin);
        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    @Transactional
    public UserResponseDto findById(UUID id) {
        User user = userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new UserNotFundException(USER_NOT_FOUND + id));
        updateLastLogin(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto create(UserRequestDto userRequestDto) {
        validateMail(userRequestDto.getEmail());

        User userToSave = userMapper.toEntity(userRequestDto);
        userToSave.setId(UUID.randomUUID());
        userToSave.setIsActive(true);
        userToSave.setToken(jwtService.generateToken(userRequestDto.getEmail()) );

        return userMapper.toDto(userRepository.save(userToSave));

    }

    private void validateMail(String email) {
        if (userRepository.findByEmailAndIsActiveTrue(email).isPresent()) {
            throw new UserEmailAlreadyExistException(EMAIL_EXIST);
        }
    }

    @Override
    @Transactional
    public UserResponseDto update(UUID id, UserRequestUpdateDto userRequestUpdateDto) {

        User userToModified = userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new UserNotFundException(USER_NOT_FOUND + id));

        if(!userToModified.getEmail().equals(userRequestUpdateDto.getEmail())
                && userRepository.findByEmailAndIsActiveTrue(userRequestUpdateDto.getEmail()).isPresent()){
            throw new UserEmailAlreadyExistException(EMAIL_EXIST);
        }

        userToModified.setName(userRequestUpdateDto.getName());
        userToModified.setEmail(userRequestUpdateDto.getEmail());
        userToModified.setPassword(userRequestUpdateDto.getPassword());
        userToModified.setIsActive(userRequestUpdateDto.getIsActive());
        userToModified.setModified(LocalDateTime.now());
        userToModified.setLastLogin(LocalDateTime.now());


        if(userRequestUpdateDto.getPhones() != null) {
            userToModified.getPhones().clear();
            userToModified.getPhones().addAll(convertPhonesToEntity(userRequestUpdateDto.getPhones()));
        }

        return userMapper.toDto(userRepository.save(userToModified));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        try{
            User userToDeactivate = userRepository.findByIdAndIsActiveTrue(id)
                    .orElseThrow(() -> new UserNotFundException(USER_NOT_FOUND + id));

            userToDeactivate.setIsActive(false);

            userRepository.save(userToDeactivate);
        }catch (Exception e){
            throw new UserServerException(e.getLocalizedMessage());
        }

    }


    private List<Phone> convertPhonesToEntity(List<PhoneRequestDto> phones) {
        return phones.stream()
                .map(phoneMapper::toEntity)
                .toList();
    }

    private void updateLastLogin(User user) {
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
