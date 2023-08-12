package com.likelion.daroomi.nuroomi.controller;

import com.likelion.daroomi.nuroomi.domain.Application;
import com.likelion.daroomi.nuroomi.dto.GetApplicationResponseDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessOrFailResponseDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;
import com.likelion.daroomi.nuroomi.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//TODO 전체적인 예외처리(예외 코드 및 예외 메세지 획일화)

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessOrFailResponseDto> createApplication(
        @RequestBody Application application) {
        SuccessResponseDto responseDto = applicationService.createApplication(application,
            application.getId());
        return makeText(responseDto);
    }

    @GetMapping("/get")
    public ResponseEntity<GetApplicationResponseDto> getApplication(
        @RequestParam Long id) {
        SendIdRequestDto sendIdRequestDto = new SendIdRequestDto(id);
        GetApplicationResponseDto responseDto = applicationService.getApplication(sendIdRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/modify")
    public ResponseEntity<SuccessResponseDto> modifyApplication(
        @RequestBody Application application) {
        SuccessResponseDto responseDto = applicationService.modifyApplication(application);
        return ResponseEntity.ok(responseDto);
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
