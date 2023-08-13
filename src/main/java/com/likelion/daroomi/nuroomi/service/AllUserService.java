package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.AllUser;
import com.likelion.daroomi.nuroomi.dto.ChangePasswordRequestDto;
import com.likelion.daroomi.nuroomi.dto.GetUserNameResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginRequestDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;


public interface AllUserService {

    SuccessResponseDto createUser(AllUser allUser);

    GetUserNameResponseDto getUserName(SendIdRequestDto sendIdRequestDto);

    SuccessResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);
}
