package com.nisum.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneResponseDto {
    private String number;

    @JsonProperty(value = "city_code")
    private String cityCode;

    @JsonProperty(value = "country_code")
    private String countryCode;
}
