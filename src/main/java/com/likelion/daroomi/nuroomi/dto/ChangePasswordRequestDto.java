package com.likelion.daroomi.nuroomi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChangePasswordRequestDto {

    private Long id;
    private String password;
}
