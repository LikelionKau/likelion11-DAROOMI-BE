package com.likelion.daroomi.nuroomi.dto;

import com.likelion.daroomi.nuroomi.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class LoginConsultantResponseDto {

    private Long id;
    private String loginId;
    private String password;
    private String email;
    private String name;
    private String phoneNumber;
    private String socialNumber;
    private Address address;
    private String profileImage;
    private boolean isAccepted;
    private Integer point;
    private String bankCompany;
    private String bankAccount;
}
