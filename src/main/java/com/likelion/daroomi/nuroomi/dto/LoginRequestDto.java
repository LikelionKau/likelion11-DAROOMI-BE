package com.likelion.daroomi.nuroomi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequestDto {

    private String loginId;
    private String password;
}
