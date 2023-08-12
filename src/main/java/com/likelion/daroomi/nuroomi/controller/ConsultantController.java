package com.likelion.daroomi.nuroomi.controller;

import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.dto.ChangePasswordRequestDto;
import com.likelion.daroomi.nuroomi.dto.ConsultantInfoModifyRequestDto;
import com.likelion.daroomi.nuroomi.dto.GetUserNameResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginConsultantResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginRequestDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessOrFailResponseDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;
import com.likelion.daroomi.nuroomi.service.ConsultantService;
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
@RequestMapping("/consultant")
public class ConsultantController {

    private final ConsultantService consultantService;

    @Autowired
    public ConsultantController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessOrFailResponseDto> createConsultant(
        @RequestBody Consultant consultant) {
        SuccessResponseDto responseDto = consultantService.createUser(consultant);
        return makeText(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginConsultantResponseDto> loginUser(
        @RequestBody LoginRequestDto loginRequestDto) {
        LoginConsultantResponseDto findConsultant = consultantService.loginUser(loginRequestDto);
        return ResponseEntity.ok(findConsultant);
    }

    @GetMapping("/getUserName")
    public ResponseEntity<GetUserNameResponseDto> getUserName(@RequestParam Long id) {
        SendIdRequestDto sendIdRequestDto = new SendIdRequestDto(id);
        GetUserNameResponseDto responseDto = consultantService.getUserName(sendIdRequestDto);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/modifyUser")
    public ResponseEntity<SuccessOrFailResponseDto> modifyUser(@RequestBody
    ConsultantInfoModifyRequestDto consultantInfoModifyRequestDto) {
        SuccessResponseDto responseDto = consultantService.modifyUser(
            consultantInfoModifyRequestDto);
        return makeText(responseDto);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<SuccessOrFailResponseDto> changePassword(
        @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        SuccessResponseDto responseDto = consultantService.changePassword(changePasswordRequestDto);
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
