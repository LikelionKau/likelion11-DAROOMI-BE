package com.likelion.daroomi.nuroomi.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequestDto implements Serializable {

    private String loginId;
    private String password;
}
