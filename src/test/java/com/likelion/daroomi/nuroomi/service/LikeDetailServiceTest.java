package com.likelion.daroomi.nuroomi.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.LikeDetail;
import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.domain.Consultantee;
import com.likelion.daroomi.nuroomi.dto.LikeDetailDto;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultanteeRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultingRepository;
import com.likelion.daroomi.nuroomi.repository.LikeDetailRepository;
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
class LikeDetailServiceTest {

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
    @Autowired
    LikeDetailService likeDetailService;
    @Autowired
    LikeDetailRepository likeDetailRepository;

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
    @DisplayName("LikeDetail 속성 조회")
    public void createLikeDetailTest() throws Exception {
        //given
        Consulting consulting = matching();
        LikeDetailDto likeDetailDto = new LikeDetailDto();
        likeDetailDto.setDislikes(true);
        likeDetailDto.setReason("I hate you!");

        //when
        Long likeDetailId = likeDetailService.createLikeDetail(consulting.getId(), likeDetailDto);
        LikeDetail findLikeDetail = likeDetailRepository.findById(likeDetailId).get();

        //then
        assertThat(findLikeDetail.getConsultant().getId()).isEqualTo(1L);
        assertThat(findLikeDetail.getConsulting().getId()).isEqualTo(consulting.getId());
        assertThat(findLikeDetail.getReason()).isEqualTo("I hate you!");
    }

    private Consulting matching() {
        waitingList.joinWaitingList(1L, true);
        waitingList.joinWaitingList(1L, false);

        Long startId = consultingService.startConsulting(1L);
        Long endId = consultingService.endConsulting(startId, "voice", null);

        return consultingRepository.findById(endId).get();
    }
}