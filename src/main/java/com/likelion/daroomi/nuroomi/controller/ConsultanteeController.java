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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public ConsultanteeController(ConsultanteeService consultanteeService,
        PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.consultanteeService = consultanteeService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessOrFailResponseDto> createConsultantee(
        @RequestBody Consultantee consultantee) {
        consultantee.encodingPassword(passwordEncoder.encode(consultantee.getPassword()));
        SuccessResponseDto responseDto = consultanteeService.createUser(consultantee);
        return makeText(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginConsultanteeResponseDto> loginUser(
        @RequestBody LoginRequestDto loginRequestDto) {
        LoginConsultanteeResponseDto findConsultantee = consultanteeService.loginUser(
            loginRequestDto);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            loginRequestDto.getLoginId(), loginRequestDto.getPassword());

        try {
            // 인증 시도
            Authentication authenticated = authenticationManager.authenticate(authentication);

            // 인증 성공 시
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            return ResponseEntity.status(HttpStatus.OK).body(findConsultantee);
        } catch (AuthenticationException e) {
            // 인증 실패 시
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(findConsultantee);
        }
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
        changePasswordRequestDto.setPassword(passwordEncoder.encode(
            changePasswordRequestDto.getPassword()));
        SuccessResponseDto responseDto = consultanteeService.changePassword(
            changePasswordRequestDto);
        return makeText(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessOrFailResponseDto> logOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok(new SuccessOrFailResponseDto("성공"));
        } else {
            return ResponseEntity.ok(new SuccessOrFailResponseDto("실패"));
        }
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
