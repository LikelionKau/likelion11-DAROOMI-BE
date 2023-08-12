package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.AllUser;
import com.likelion.daroomi.nuroomi.dto.ConsultanteeInfoModifyRequestDto;
import com.likelion.daroomi.nuroomi.dto.LoginConsultantResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginConsultanteeResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;

public interface ConsultanteeService extends AllUserService {

    SuccessResponseDto modifyUser(
        ConsultanteeInfoModifyRequestDto consultanteeInfoModifyRequestDto);

    LoginConsultanteeResponseDto loginUser(LoginRequestDto loginRequestDto);
}
