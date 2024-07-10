package com.nisum.challenge.dto;

import com.nisum.challenge.validator.EmailConstraint;
import com.nisum.challenge.validator.PasswordConstraint;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class UserRequestDto {

    private String name;
    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;
    private List<PhoneRequestDto> phones;

}
