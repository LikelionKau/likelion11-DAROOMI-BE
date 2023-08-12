package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.dto.GetPointDetailResponseDto;
import com.likelion.daroomi.nuroomi.dto.GetPointResponseDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;
import com.likelion.daroomi.nuroomi.dto.UsePointRequestDto;
import java.util.List;

public interface PointUseDetailService {

    GetPointResponseDto getPoint(SendIdRequestDto sendIdRequestDto);

    SuccessResponseDto usePoint(UsePointRequestDto usePointRequestDto);

    List<GetPointDetailResponseDto> getPointDetail(SendIdRequestDto sendIdRequestDto);
}
