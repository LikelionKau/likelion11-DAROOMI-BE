package com.likelion.daroomi.nuroomi.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.LikeDetail;
import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.domain.Consultantee;
import com.likelion.daroomi.nuroomi.dto.consulting.EndConsultingDto;
import com.likelion.daroomi.nuroomi.dto.consulting.JoinWaitingListDto;
import com.likelion.daroomi.nuroomi.dto.consulting.LikeDetailDto;
import com.likelion.daroomi.nuroomi.dto.consulting.StartConsultingDto;
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
    @DisplayName("LikeDetail 속성 조회")
    public void createLikeDetailTest() throws Exception {
        //given
        Consulting consulting = matching();
        System.out.println("consulting = " + consulting);
        LikeDetailDto likeDetailDto = new LikeDetailDto(false, true, "Hate U");

        //when
        Long likeDetailId = likeDetailService.createLikeDetail(consulting.getId(), likeDetailDto);
        System.out.println("likeDetailId = " + likeDetailId);
        LikeDetail findLikeDetail = likeDetailRepository.findById(likeDetailId).get();

        //then
        assertThat(findLikeDetail.getConsultant().getId()).isEqualTo(1L);
        assertThat(findLikeDetail.getConsulting().getId()).isEqualTo(consulting.getId());
        assertThat(findLikeDetail.getReason()).isEqualTo("Hate U");
    }

    private Consulting matching() {

        waitingList.joinWaitingList(new JoinWaitingListDto(1L, true));
        waitingList.joinWaitingList(new JoinWaitingListDto(1L, false));

        StartConsultingDto startConsultingDto = consultingService.startConsulting(1L);

        EndConsultingDto endConsultingDto = new EndConsultingDto(
            startConsultingDto.getConsultingId(), "voice", null);
        Long endId = consultingService.endConsulting(endConsultingDto);

        return consultingRepository.findById(endId).get();
    }
}