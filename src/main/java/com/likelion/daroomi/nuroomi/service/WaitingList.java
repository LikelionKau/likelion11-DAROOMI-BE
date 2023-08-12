package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.domain.Consultantee;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultanteeRepository;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class WaitingList {

    private final ConsultantRepository consultantRepository;
    private final ConsultanteeRepository consultanteeRepository;

    private final LinkedHashMap<Long, Consultant> consultantList = new LinkedHashMap<>();
    private final LinkedList<Long> consultanteeList = new LinkedList<>();

    public void joinWaitingList(Long userId, boolean isConsultant) {

        if (isConsultant) {
            consultantList.put(userId, consultantRepository.findById(userId).get());
        } else {
            consultanteeList.add(userId);
        }
    }

    public void quitWaitingList(Long userId, boolean isConsultant) {

        if (!isConsultant) {
            for (int i = 0; i < consultanteeList.size(); i++) {
                if (Objects.equals(consultanteeList.get(i), userId)) {
                    consultanteeList.remove(i);
                    break;
                }
            }
        } else {
            consultantList.remove(userId);
        }
    }

    public Participants getParticipants(Long consultantId) {

        Consultant consultant = consultantList.get(consultantId);
        Consultantee consultantee = consultanteeRepository.findById(consultanteeList.get(0)).get();

        consultantList.remove(consultantId);
        consultanteeList.remove(0);

        return new Participants(consultant, consultantee);
    }

    @Getter
    @AllArgsConstructor
    public static class Participants {

        private Consultant consultant;
        private Consultantee consultantee;
    }
}