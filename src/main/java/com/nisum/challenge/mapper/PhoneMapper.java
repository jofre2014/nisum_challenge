package com.nisum.challenge.mapper;

import com.nisum.challenge.dto.PhoneRequestDto;
import com.nisum.challenge.dto.PhoneResponseDto;
import com.nisum.challenge.entity.Phone;
import org.mapstruct.Mapper;

@Mapper
public interface PhoneMapper {
    PhoneResponseDto toDto(Phone phone);

    Phone toEntity(PhoneRequestDto phoneRequestDto);
}
