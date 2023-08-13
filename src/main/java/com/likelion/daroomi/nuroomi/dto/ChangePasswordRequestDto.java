package com.likelion.daroomi.nuroomi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangePasswordRequestDto {

    private Long id;
    private String password;
}
