package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.Application;
import com.likelion.daroomi.nuroomi.dto.GetApplicationResponseDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;

public interface ApplicationService {

    SuccessResponseDto createApplication(Application application, Long consultantId);

    GetApplicationResponseDto getApplication(SendIdRequestDto sendIdRequestDto);

    SuccessResponseDto modifyApplication(Application application);
}
