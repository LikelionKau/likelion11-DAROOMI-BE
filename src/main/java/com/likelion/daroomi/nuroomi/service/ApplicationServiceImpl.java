package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.Application;
import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import com.likelion.daroomi.nuroomi.dto.GetApplicationResponseDto;
import com.likelion.daroomi.nuroomi.dto.SendIdRequestDto;
import com.likelion.daroomi.nuroomi.dto.SuccessResponseDto;
import com.likelion.daroomi.nuroomi.exception.ApplicationNotFoundException;
import com.likelion.daroomi.nuroomi.exception.UserNotFoundException;
import com.likelion.daroomi.nuroomi.repository.ApplicationRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ConsultantRepository consultantRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
        ConsultantRepository consultantRepository) {
        this.applicationRepository = applicationRepository;
        this.consultantRepository = consultantRepository;
    }

    @Override
    @Transactional
    public SuccessResponseDto createApplication(Application application, Long consultantId) {
        Consultant consultant = consultantRepository.findById(consultantId)
            .orElseThrow(UserNotFoundException::new);
        application.setId(consultantId);
        application.setConsultant(consultant);
        applicationRepository.save(application);
        return new SuccessResponseDto(true);
    }


    @Override
    @Transactional
    public GetApplicationResponseDto getApplication(SendIdRequestDto sendIdRequestDto) {
        try {
            Application application = makeApplication(sendIdRequestDto.getId());
            return new GetApplicationResponseDto(application.getQuestion1(),
                application.getQuestion2(), application.getQuestion3());
        } catch (ApplicationNotFoundException e) {
            e.getStackTrace();
            return new GetApplicationResponseDto();
        }
    }

    @Override
    @Transactional
    public SuccessResponseDto modifyApplication(Application application) {
        try {
            Application modifyApplication = makeApplication(application.getId());
            modifyApplication.modifyInfo(application.getQuestion1(), application.getQuestion2(),
                application.getQuestion3());
            applicationRepository.save(modifyApplication);
            return new SuccessResponseDto(true);
        } catch (ApplicationNotFoundException e) {
            e.getStackTrace();
            return new SuccessResponseDto(false);
        }
    }

    public Application makeApplication(Long id) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isPresent()) {
            return optionalApplication.get();
        } else {
            throw new ApplicationNotFoundException("신청서 없음");
        }
    }
}
