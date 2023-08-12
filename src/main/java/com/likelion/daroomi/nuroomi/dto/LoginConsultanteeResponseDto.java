package com.likelion.daroomi.nuroomi.dto;

import com.likelion.daroomi.nuroomi.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginConsultanteeResponseDto {

    private Long id;
    private String loginId;
    private String password;
    private String email;
    private String name;
    private String phoneNumber;
    private String socialNumber;
    private Address address;
}
