package com.likelion.daroomi.nuroomi.service;


import com.likelion.daroomi.nuroomi.domain.AllUser;
import com.likelion.daroomi.nuroomi.domain.user.Consultantee;
import com.likelion.daroomi.nuroomi.dto.ChangePasswordRequestDto;
import com.likelion.daroomi.nuroomi.dto.ConsultanteeInfoModifyRequestDto;
import com.likelion.daroomi.nuroomi.dto.GetUserNameResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginConsultanteeResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginRequestDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;
import com.likelion.daroomi.nuroomi.exception.UserNotFoundException;
import com.likelion.daroomi.nuroomi.repository.ConsultanteeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultanteeServiceImpl implements ConsultanteeService {

    private final ConsultanteeRepository consultanteeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ConsultanteeServiceImpl(ConsultanteeRepository consultanteeRepository,
        PasswordEncoder passwordEncoder) {
        this.consultanteeRepository = consultanteeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public SuccessResponseDto createUser(AllUser consultantee) {
        consultanteeRepository.save((Consultantee) consultantee);
        return new SuccessResponseDto(true);
    }

    @Override
    @Transactional
    public LoginConsultanteeResponseDto loginUser(LoginRequestDto loginRequestDto) {
        Optional<Consultantee> optionalConsultant = consultanteeRepository.findByLoginId(
            loginRequestDto.getLoginId());
        if (optionalConsultant.isPresent()) {
            Consultantee consultantee = optionalConsultant.get();
            if (passwordEncoder.matches(loginRequestDto.getPassword(),
                consultantee.getPassword())) {
                return makeLoginConsultanteeResponseDto(consultantee);
            } else {
                throw new RuntimeException("비밀번호 틀림");
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    private LoginConsultanteeResponseDto makeLoginConsultanteeResponseDto(
        Consultantee consultantee) {
        return LoginConsultanteeResponseDto.builder().id(
            consultantee.getId()).loginId(consultantee.getLoginId()).password(
            consultantee.getPassword()).email(consultantee.getEmail()).name(
            consultantee.getName()).phoneNumber(consultantee.getPhoneNumber()).socialNumber(
            consultantee.getSocialNumber()).address(consultantee.getAddress()).build();
    }

    public Consultantee makeConsultantee(Long id) {
        Optional<Consultantee> optionalConsultantee = consultanteeRepository.findById(id);
        if (optionalConsultantee.isPresent()) {
            return optionalConsultantee.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    @Transactional
    public GetUserNameResponseDto getUserName(SendIdRequestDto sendIdRequestDto) {
        try {
            Consultantee consultantee = makeConsultantee(sendIdRequestDto.getId());
            return new GetUserNameResponseDto(consultantee.getName());
        } catch (UserNotFoundException e) {
            e.getStackTrace();
        }
        throw new UserNotFoundException();
    }

    @Override
    @Transactional
    public SuccessResponseDto modifyUser(
        ConsultanteeInfoModifyRequestDto consultanteeInfoModifyRequestDto) {
        try {
            Consultantee modifyConsultantee = makeConsultantee(
                consultanteeInfoModifyRequestDto.getId());
            modifyConsultantee.modifyInfo(
                consultanteeInfoModifyRequestDto.getAddress(),
                consultanteeInfoModifyRequestDto.getPhoneNumber(),
                consultanteeInfoModifyRequestDto.getEmail());
            consultanteeRepository.save(modifyConsultantee);
            return new SuccessResponseDto(true);
        } catch (UserNotFoundException e) {
            e.getStackTrace();
            return new SuccessResponseDto(false);
        }
    }

    @Override
    @Transactional
    public SuccessResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        try {
            Consultantee modifyPasswordConsultantee = makeConsultantee(
                changePasswordRequestDto.getId());
            modifyPasswordConsultantee.modifyPassword(changePasswordRequestDto);
            consultanteeRepository.save(modifyPasswordConsultantee);
            return new SuccessResponseDto(true);
        } catch (UserNotFoundException e) {
            e.getStackTrace();
            return new SuccessResponseDto(false);
        }
    }
}
