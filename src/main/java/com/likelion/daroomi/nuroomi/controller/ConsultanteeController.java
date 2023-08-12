package com.likelion.daroomi.nuroomi.controller;

import com.likelion.daroomi.nuroomi.domain.user.Consultantee;
import com.likelion.daroomi.nuroomi.dto.ChangePasswordRequestDto;
import com.likelion.daroomi.nuroomi.dto.ConsultanteeInfoModifyRequestDto;
import com.likelion.daroomi.nuroomi.dto.GetUserNameResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginConsultanteeResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginRequestDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessOrFailResponseDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;
import com.likelion.daroomi.nuroomi.service.ConsultanteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//TODO 예외최적화

@RestController
@RequestMapping("/consultantee")
public class ConsultanteeController {

    private final ConsultanteeService consultanteeService;

    @Autowired
    public ConsultanteeController(ConsultanteeService consultanteeService) {
        this.consultanteeService = consultanteeService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessOrFailResponseDto> createConsultantee(
        @RequestBody Consultantee consultantee) {
        SuccessResponseDto responseDto = consultanteeService.createUser(consultantee);
        return makeText(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginConsultanteeResponseDto> loginUser(
        @RequestBody LoginRequestDto loginRequestDto) {
        LoginConsultanteeResponseDto findConsultantee = consultanteeService.loginUser(
            loginRequestDto);
        return ResponseEntity.ok(findConsultantee);
    }

    @GetMapping("/getUserName")
    public ResponseEntity<GetUserNameResponseDto> getUserName(@RequestParam Long id) {
        SendIdRequestDto sendIdRequestDto = new SendIdRequestDto(id);
        GetUserNameResponseDto responseDto = consultanteeService.getUserName(sendIdRequestDto);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/modifyUser")
    public ResponseEntity<SuccessOrFailResponseDto> modifyUser(
        @RequestBody ConsultanteeInfoModifyRequestDto consultanteeInfoModifyRequestDto) {
        SuccessResponseDto responseDto = consultanteeService.modifyUser(
            consultanteeInfoModifyRequestDto);
        return makeText(responseDto);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<SuccessOrFailResponseDto> changePassword(
        @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        SuccessResponseDto responseDto = consultanteeService.changePassword(
            changePasswordRequestDto);
        return makeText(responseDto);
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
