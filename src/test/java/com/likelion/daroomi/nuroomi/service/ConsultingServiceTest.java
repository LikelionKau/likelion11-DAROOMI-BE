package com.likelion.daroomi.nuroomi.service;

import static org.assertj.core.api.Assertions.*;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.dto.consulting.EndConsultingDto;
import com.likelion.daroomi.nuroomi.dto.consulting.JoinWaitingListDto;
import com.likelion.daroomi.nuroomi.dto.consulting.StartConsultingDto;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultanteeRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultingRepository;
import java.sql.Timestamp;
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

    @Test
    @DisplayName("상담 종료 후 속성 조회")
    public void startConsultingTest() {

        //given
        waitingList.joinWaitingList(new JoinWaitingListDto(1L, true));
        waitingList.joinWaitingList(new JoinWaitingListDto(1L, false));

        StartConsultingDto startConsultingDto = consultingService.startConsulting(1L);

        EndConsultingDto endConsultingDto = new EndConsultingDto(
            startConsultingDto.getConsultingId(), "voice", null);
        Long endId = consultingService.endConsulting(endConsultingDto);

        Consulting savedConsulting = consultingRepository.findById(endId).get();

        //when
        String consultantLoginId = savedConsulting.getConsultant().getLoginId();
        String consultanteeLoginId = savedConsulting.getConsultantee().getLoginId();
        String voiceUrl = savedConsulting.getVoiceUrl();
        String videoUrl = savedConsulting.getVideoUrl();
        Timestamp createdTime = savedConsulting.getCreatedTime();

        //then
        assertThat(consultantLoginId).isEqualTo("consultantId");
        assertThat(consultanteeLoginId).isEqualTo("consultanteeId");
        assertThat(voiceUrl).isEqualTo("voice");
        assertThat(videoUrl).isEqualTo(null);
        log.info("Print createdTime={}", createdTime);

    }
}