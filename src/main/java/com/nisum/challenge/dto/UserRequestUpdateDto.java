package com.nisum.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nisum.challenge.validator.EmailConstraint;
import com.nisum.challenge.validator.PasswordConstraint;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequestUpdateDto {

    private String name;

    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;
    private List<PhoneRequestDto> phones;

    @JsonProperty(value = "is_active")
    private Boolean isActive;
}
