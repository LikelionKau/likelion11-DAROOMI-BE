package com.likelion.daroomi.nuroomi.dto;

import com.likelion.daroomi.nuroomi.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConsultantInfoModifyRequestDto {

    private Long id;
    private String profileImage;
    private String bankCompany;
    private String bankAccount;
    private String email;
    private String phoneNumber;
    private Address address;
}
