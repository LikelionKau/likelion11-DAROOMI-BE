package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.domain.Consultantee;
import com.likelion.daroomi.nuroomi.dto.consulting.ConsultantProfileDto;
import com.likelion.daroomi.nuroomi.dto.consulting.ConsultanteeProfileDto;
import com.likelion.daroomi.nuroomi.dto.consulting.EndConsultingDto;
import com.likelion.daroomi.nuroomi.dto.consulting.StartConsultingDto;
import com.likelion.daroomi.nuroomi.repository.ConsultingRepository;
import com.likelion.daroomi.nuroomi.service.WaitingList.Participants;
import java.time.LocalTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultingService {
    private final ConsultingRepository consultingRepository;
    private final WaitingList waitingList;

    @Transactional
    public StartConsultingDto startConsulting(Long consultantId) {

        Participants participants = waitingList.getParticipants(consultantId);
        Consultant consultant = participants.getConsultant();
        Consultantee consultantee = participants.getConsultantee();

        Consulting consulting = Consulting.initConsulting(consultant, consultantee);

        consultingRepository.save(consulting);

        return new StartConsultingDto(consulting.getId(),
            consultantId, consultantee.getId());
    }

    @Transactional
    public Long endConsulting(EndConsultingDto endConsultingDto) {
        Optional<Consulting> baseConsulting = consultingRepository.findById(endConsultingDto.getConsultingId());
        Consulting consulting = baseConsulting.get().createConsulting(endConsultingDto);

        addPointToConsultant(consulting);

        consultingRepository.save(consulting);

        return consulting.getId();
    }

    private static void addPointToConsultant(Consulting consulting) {
        //todo 상담 하나에 몇 포인트?
        final int addPoint = 1000;
        consulting.getConsultant().addPoint(addPoint);
    }

    public ConsultantProfileDto setConsultantProfileDto(Long consultingId) {

        Consulting consulting = consultingRepository.findById(consultingId).get();
        Consultant consultant = consulting.getConsultant();

        return new ConsultantProfileDto(consultant.getName(), consultant.getProfileImage());
    }

    public ConsultanteeProfileDto setConsultanteeProfileDto(Long consultingId) {

        Consulting consulting = consultingRepository.findById(consultingId).get();
        Consultantee consultantee = consulting.getConsultantee();

        return new ConsultanteeProfileDto(consultantee.getName());
    }

    public LocalTime setConsultingEndInfoDto(Long consultingId) {

        Consulting consulting = consultingRepository.findById(consultingId).get();
        LocalTime duration = consulting.getDuration();

        return duration;
    }
}
