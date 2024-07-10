package com.nisum.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<PhoneResponseDto> phones;
    private LocalDateTime created;
    private LocalDateTime modified;

    @JsonProperty(value = "last_login")
    private LocalDateTime lastLogin;

    private String token;

    @JsonProperty(value = "is_active")
    private Boolean isActive;
}
