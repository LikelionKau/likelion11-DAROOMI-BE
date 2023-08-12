package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import com.likelion.daroomi.nuroomi.domain.user.Consultantee;
import com.likelion.daroomi.nuroomi.repository.ConsultingRepository;
import com.likelion.daroomi.nuroomi.service.WaitingList.Participants;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultingService {
    private final ConsultingRepository consultingRepository;
    private final WaitingList waitingList;

    public Long startConsulting(Long consultantId) {

        Participants participants = waitingList.getParticipants(consultantId);
        Consultant consultant = participants.getConsultant();
        Consultantee consultantee = participants.getConsultantee();

        Consulting consulting = Consulting.initConsulting(consultant, consultantee);

        consultingRepository.save(consulting);

        return consulting.getId();
    }

    public Long endConsulting(Long consultingId, String voiceUrl, String videoUrl) {
        Optional<Consulting> baseConsulting = consultingRepository.findById(consultingId);
        Consulting consulting = baseConsulting.get().createConsulting(voiceUrl, videoUrl);

        consultingRepository.save(consulting);

        return consulting.getId();
    }

}
