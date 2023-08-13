package com.likelion.daroomi.nuroomi.service;


import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.domain.Consultantee;
import com.likelion.daroomi.nuroomi.dto.consulting.JoinWaitingListDto;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultanteeRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class WaitingListTest {

    @Autowired
    ConsultantRepository consultantRepository;
    @Autowired
    ConsultanteeRepository consultanteeRepository;
    @Autowired
    ConsultingRepository consultingRepository;
    @Autowired
    WaitingList waitingList;

    @BeforeEach()
    void tempData() {

        Consultant consultant1 = new Consultant("consultant1Id", "consultant1Pw",
            "consultant1Profile", true);
        Consultant consultant2 = new Consultant("consultant2Id", "consultant2Pw",
            "consultant2Profile", true);

        consultantRepository.save(consultant1);
        consultantRepository.save(consultant2);

        Consultantee consultantee = new Consultantee("consultanteeId", "consultanteePw");

        consultanteeRepository.save(consultantee);
    }

    @Test
    @DisplayName("waiting list의 참여자 수는 (join - quit)이어야한다.")
    public void matching() throws Exception {

        //given
        waitingList.joinWaitingList(new JoinWaitingListDto(1L, true));
        waitingList.joinWaitingList(new JoinWaitingListDto(1L, false));


        //when
        waitingList.quitWaitingList(2L, true);

        //then
        Assertions.assertThat(waitingList.getConsultantList().size()).isEqualTo(1);
        Assertions.assertThat(waitingList.getConsultanteeList().size()).isEqualTo(1);
    }
}