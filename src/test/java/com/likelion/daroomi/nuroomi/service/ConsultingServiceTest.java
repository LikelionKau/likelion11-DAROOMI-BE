package com.likelion.daroomi.nuroomi.service;

import static org.assertj.core.api.Assertions.*;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.domain.Consultantee;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultanteeRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultingRepository;
import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ConsultingServiceTest {

    Logger log = LoggerFactory.getLogger(ConsultingServiceTest.class);

    @Autowired
    ConsultantRepository consultantRepository;
    @Autowired
    ConsultanteeRepository consultanteeRepository;
    @Autowired
    ConsultingRepository consultingRepository;
    @Autowired
    WaitingList waitingList;
    @Autowired
    ConsultingService consultingService;

    @BeforeEach()
    void tempData() {
        Consultant consultant1 = new Consultant();
        consultant1.setLoginId("consultant1Id");
        consultant1.setPassword("consultant1Pw");
        consultant1.setProfileImage("consultant1Profile");

        Consultant consultant2 = new Consultant();
        consultant2.setLoginId("consultant2Id");
        consultant2.setPassword("consultant2Pw");
        consultant2.setProfileImage("consultant2Profile");

        consultantRepository.save(consultant1);
        consultantRepository.save(consultant2);

        Consultantee consultantee = new Consultantee();
        consultantee.setLoginId("consultanteeId");
        consultantee.setPassword("consultanteePw");
        consultanteeRepository.save(consultantee);
    }

    @Test
    @DisplayName("상담 종료 후 속성 조회")
    public void startConsultingTest() {

        //given
        waitingList.joinWaitingList(1L, true);
        waitingList.joinWaitingList(1L, false);

        Long startId = consultingService.startConsulting(1L);
        Long endId = consultingService.endConsulting(startId, "voice", null);

        Consulting savedConsulting = consultingRepository.findById(endId).get();

        //when
        String consultantLoginId = savedConsulting.getConsultant().getLoginId();
        String consultanteeLoginId = savedConsulting.getConsultantee().getLoginId();
        String voiceUrl = savedConsulting.getVoiceUrl();
        String videoUrl = savedConsulting.getVideoUrl();
        Timestamp createdTime = savedConsulting.getCreatedTime();

        //then
        assertThat(consultantLoginId).isEqualTo("consultant1Id");
        assertThat(consultanteeLoginId).isEqualTo("consultanteeId");
        assertThat(voiceUrl).isEqualTo("voice");
        assertThat(videoUrl).isEqualTo(null);
        log.info("Print createdTime={}", createdTime);

    }
}