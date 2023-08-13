package com.likelion.daroomi.nuroomi.dto;

import com.likelion.daroomi.nuroomi.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConsultanteeInfoModifyRequestDto {

    private Long id;
    private String email;
    private String phoneNumber;
    private Address address;
}
