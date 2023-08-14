package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.dto.ConsultantInfoModifyRequestDto;
import com.likelion.daroomi.nuroomi.dto.LoginConsultantResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;

public interface ConsultantService extends AllUserService {

    Consultant makeConsultant(Long id);

    Consultant makeLazyConsultant(Long id);

    SuccessResponseDto modifyUser(ConsultantInfoModifyRequestDto consultantInfoModifyRequestDto);

    LoginConsultantResponseDto loginUser(LoginRequestDto loginRequestDto);

}
