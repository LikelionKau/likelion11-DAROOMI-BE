package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.AllUser;
import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import com.likelion.daroomi.nuroomi.dto.ChangePasswordRequestDto;
import com.likelion.daroomi.nuroomi.dto.ConsultantInfoModifyRequestDto;
import com.likelion.daroomi.nuroomi.dto.GetUserNameResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginConsultantResponseDto;
import com.likelion.daroomi.nuroomi.dto.LoginRequestDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;
import com.likelion.daroomi.nuroomi.exception.UserNotFoundException;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultantServiceImpl implements ConsultantService {

    private final ConsultantRepository consultantRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ConsultantServiceImpl(ConsultantRepository consultantRepository,
        PasswordEncoder passwordEncoder) {
        this.consultantRepository = consultantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public SuccessResponseDto createUser(AllUser consultant) {
        consultantRepository.save((Consultant) consultant);
        return new SuccessResponseDto(true);
    }

    @Override
    @Transactional
    public LoginConsultantResponseDto loginUser(LoginRequestDto loginRequestDto) {
        Optional<Consultant> optionalConsultant = consultantRepository.findByLoginId(
            loginRequestDto.getLoginId());
        if (optionalConsultant.isPresent()) {
            Consultant consultant = optionalConsultant.get();
            if (passwordEncoder.matches(loginRequestDto.getPassword(), consultant.getPassword())) {
                return makeLoginConsultantResponseDto(consultant);
            } else {
                throw new RuntimeException("비밀번호 틀림");
            }
        } else {
            throw new UserNotFoundException();
        }

    }

    private LoginConsultantResponseDto makeLoginConsultantResponseDto(Consultant consultant) {
        return LoginConsultantResponseDto.builder().id(
                consultant.getId()).loginId(consultant.getLoginId()).password(
                consultant.getPassword()).email(consultant.getEmail()).name(
                consultant.getName()).phoneNumber(consultant.getPhoneNumber()).socialNumber(
                consultant.getSocialNumber()).address(consultant.getAddress())
            .profileImage(consultant.getProfileImage()).isAccepted(
                consultant.getIsAccepted()).point(consultant.getPoint()).bankCompany(
                consultant.getBankCompany()).bankAccount(consultant.getBankAccount()).build();
    }

    @Override
    public Consultant makeConsultant(Long id) {
        Optional<Consultant> optionalConsultant = consultantRepository.findById(id);
        if (optionalConsultant.isPresent()) {
            return optionalConsultant.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    @Transactional
    public GetUserNameResponseDto getUserName(SendIdRequestDto sendIdRequestDto) {
        try {
            Consultant consultant = makeConsultant(sendIdRequestDto.getId());
            return new GetUserNameResponseDto(consultant.getName());
        } catch (UserNotFoundException e) {
            e.getStackTrace();
        }
        return new GetUserNameResponseDto("None");
    }

    @Override
    @Transactional
    public SuccessResponseDto modifyUser(
        ConsultantInfoModifyRequestDto consultantInfoModifyRequestDto) {
        try {
            Consultant modifyConsultant = makeConsultant(consultantInfoModifyRequestDto.getId());
            modifyConsultant.modifyInfo(consultantInfoModifyRequestDto.getProfileImage(),
                consultantInfoModifyRequestDto.getAddress(),
                consultantInfoModifyRequestDto.getPhoneNumber(),
                consultantInfoModifyRequestDto.getEmail(),
                consultantInfoModifyRequestDto.getBankCompany(),
                consultantInfoModifyRequestDto.getBankAccount());
            consultantRepository.save(modifyConsultant);
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
            Consultant modifyPasswordConsultant = makeConsultant(changePasswordRequestDto.getId());
            modifyPasswordConsultant.modifyPassword(changePasswordRequestDto);
            consultantRepository.save(modifyPasswordConsultant);
            return new SuccessResponseDto(true);
        } catch (UserNotFoundException e) {
            e.getStackTrace();
            return new SuccessResponseDto(false);
        }
    }

    @Override
    public Consultant makeLazyConsultant(Long id) {
        return consultantRepository.getReferenceById(id);
    }
}
