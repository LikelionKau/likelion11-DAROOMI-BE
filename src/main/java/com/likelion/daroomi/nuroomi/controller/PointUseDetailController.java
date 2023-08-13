package com.likelion.daroomi.nuroomi.controller;

import com.likelion.daroomi.nuroomi.dto.GetPointDetailResponseDto;
import com.likelion.daroomi.nuroomi.dto.GetPointResponseDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessOrFailResponseDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;
import com.likelion.daroomi.nuroomi.dto.UsePointRequestDto;
import com.likelion.daroomi.nuroomi.service.PointUseDetailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point/use")
public class PointUseDetailController {

    private final PointUseDetailService pointUseDetailService;

    @Autowired
    public PointUseDetailController(PointUseDetailService pointUseDetailService) {
        this.pointUseDetailService = pointUseDetailService;
    }

    @GetMapping("/getPoint")
    public ResponseEntity<GetPointResponseDto> getPoint(@RequestParam Long id) {
        SendIdRequestDto sendIdRequestDto = new SendIdRequestDto(id);
        GetPointResponseDto responseDto = pointUseDetailService.getPoint(sendIdRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/usePoint")
    public ResponseEntity<SuccessOrFailResponseDto> usePoint(
        @RequestBody UsePointRequestDto usePointRequestDto) {
        SuccessResponseDto responseDto = pointUseDetailService.usePoint(usePointRequestDto);
        return makeText(responseDto);
    }

    @GetMapping("/getPointDetail")
    public ResponseEntity<List<GetPointDetailResponseDto>> getPointDetail(
        @RequestParam Long id) {
        SendIdRequestDto sendIdRequestDto = new SendIdRequestDto(id);
        List<GetPointDetailResponseDto> responseDtoList = pointUseDetailService.getPointDetail(
            sendIdRequestDto);
        return ResponseEntity.ok(responseDtoList);
    }

    private ResponseEntity<SuccessOrFailResponseDto> makeText(
        SuccessResponseDto successResponseDto) {
        if (successResponseDto.isSuccess()) {
            return ResponseEntity.ok(new SuccessOrFailResponseDto("성공"));
        } else {
            return ResponseEntity.badRequest().body(new SuccessOrFailResponseDto("실패"));
        }
    }
}
