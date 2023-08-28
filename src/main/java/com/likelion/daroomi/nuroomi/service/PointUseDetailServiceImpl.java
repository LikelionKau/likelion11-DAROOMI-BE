package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.domain.PointUseDetail;
import com.likelion.daroomi.nuroomi.dto.GetPointDetailResponseDto;
import com.likelion.daroomi.nuroomi.dto.GetPointResponseDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;
import com.likelion.daroomi.nuroomi.dto.UsePointRequestDto;
import com.likelion.daroomi.nuroomi.exception.NegativeTotalPointException;
import com.likelion.daroomi.nuroomi.exception.UserNotFoundException;
import com.likelion.daroomi.nuroomi.repository.PointUseDetailRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointUseDetailServiceImpl implements PointUseDetailService {

    private final ConsultantService consultantService;
    private final PointUseDetailRepository pointUseDetailRepository;

    @Autowired
    public PointUseDetailServiceImpl(ConsultantService consultantService,
        PointUseDetailRepository pointUseDetailRepository) {
        this.consultantService = consultantService;
        this.pointUseDetailRepository = pointUseDetailRepository;
    }

    @Override
    @Transactional
    public GetPointResponseDto getPoint(SendIdRequestDto sendIdRequestDto) {
        try {
            Consultant modifyPasswordConsultant = consultantService.makeConsultant(
                sendIdRequestDto.getId());
            return new GetPointResponseDto(modifyPasswordConsultant.getPoint());
        } catch (UserNotFoundException e) {
            e.getStackTrace();
        }
        return new GetPointResponseDto(0);
    }

    @Override
    public SuccessResponseDto usePoint(UsePointRequestDto usePointRequestDto) {
        try {
            Consultant pointUseConsultant = consultantService.makeConsultant(
                usePointRequestDto.getConsultantId());
            pointUseConsultant.modifyTotalPoint(-usePointRequestDto.getUsePoint());
            savePointUseDetail(usePointRequestDto);
            return new SuccessResponseDto(true);
        } catch (UserNotFoundException | NegativeTotalPointException e) {
            e.getStackTrace();
            return new SuccessResponseDto(false);
        }
    }

    @Transactional
    public void savePointUseDetail(UsePointRequestDto usePointRequestDto) {
        Consultant consultant = consultantService.makeLazyConsultant(
            usePointRequestDto.getConsultantId());
        PointUseDetail newPointUseDetail = PointUseDetail.builder()
            .consultant(consultant)
            .usePoint(usePointRequestDto.getUsePoint())
            .options(usePointRequestDto.getOptions())
            .createTime(usePointRequestDto.getCreateTime())
            .build();
        pointUseDetailRepository.save(newPointUseDetail);
    }

    @Override
    @Transactional
    public List<GetPointDetailResponseDto> getPointDetail(SendIdRequestDto sendIdRequestDto) {
        List<PointUseDetail> pointUseDetailList = pointUseDetailRepository.findByConsultantId(
            sendIdRequestDto.getId());
        return pointUseDetailList.stream()
            .map(
                this::convertToGetPointDetailResponseDto).collect(Collectors.toList());
    }

    private GetPointDetailResponseDto convertToGetPointDetailResponseDto(
        PointUseDetail pointUseDetail) {
        return GetPointDetailResponseDto.builder()
            .usePoint(pointUseDetail.getUsePoint())
            .options(pointUseDetail.getOptions())
            .createTime(pointUseDetail.getCreateTime())
            .build();
    }
}
