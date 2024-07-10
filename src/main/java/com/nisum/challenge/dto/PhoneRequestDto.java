package com.nisum.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneRequestDto {

    private String number;

    @JsonProperty(value = "city_code")
    private String cityCode;

    @JsonProperty(value = "country_code")
    private String countryCode;
}
