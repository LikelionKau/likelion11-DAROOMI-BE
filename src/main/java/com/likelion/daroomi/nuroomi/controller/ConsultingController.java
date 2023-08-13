package com.likelion.daroomi.nuroomi.controller;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.dto.consulting.ConsultantProfileDto;
import com.likelion.daroomi.nuroomi.dto.consulting.ConsultanteeProfileDto;
import com.likelion.daroomi.nuroomi.dto.consulting.EndConsultingDto;
import com.likelion.daroomi.nuroomi.dto.consulting.JoinWaitingListDto;
import com.likelion.daroomi.nuroomi.dto.consulting.LikeDetailDto;
import com.likelion.daroomi.nuroomi.dto.consulting.StartConsultingDto;
import com.likelion.daroomi.nuroomi.repository.ConsultingRepository;
import com.likelion.daroomi.nuroomi.service.ConsultingService;
import com.likelion.daroomi.nuroomi.service.LikeDetailService;
import com.likelion.daroomi.nuroomi.service.WaitingList;
import java.time.LocalTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consulting")
@RequiredArgsConstructor
@Slf4j
public class ConsultingController {

    private final ConsultingService consultingService;
    private final WaitingList waitingList;
    private final LikeDetailService likeDetailService;
    private final ConsultingRepository consultingRepository;

    //todo User 역할에 따른 로직 구분

    @PostMapping("/waiting")
    public String waitConsulting(@RequestBody JoinWaitingListDto joinWaitingListDto) {

        log.info("Id = {} joined waiting list.", joinWaitingListDto.getUserId());
        waitingList.joinWaitingList(joinWaitingListDto);

        return "ok";
    }

    @GetMapping("/start/{consultantId}")
    public ResponseEntity<StartConsultingDto> joinConsulting(@PathVariable Long consultantId,
        Model model) {

        StartConsultingDto startConsultingDto = consultingService.startConsulting(consultantId);

        ConsultantProfileDto consultantProfileDto = consultingService.setConsultantProfileDto(
            startConsultingDto.getConsultantId());
        ConsultanteeProfileDto consultanteeProfileDto = consultingService.setConsultanteeProfileDto(
            startConsultingDto.getConsultanteeId());

        setJoinConsultingModel(model, consultantProfileDto, consultanteeProfileDto,
            startConsultingDto.getConsultingId());

        return ResponseEntity.ok(startConsultingDto);
    }

    private static void setJoinConsultingModel(Model model,
        ConsultantProfileDto consultantProfileDto,
        ConsultanteeProfileDto consultanteeProfileDto,
        Long consultingId) {
        model.addAttribute("consultantProfileDto", consultantProfileDto);
        model.addAttribute("consultanteeProfileDto", consultanteeProfileDto);
        model.addAttribute("consultingId", consultingId);
    }

    @PostMapping("/start/{consultantId}")
    public ResponseEntity<EndConsultingDto> quitConsulting(@PathVariable Long consultantId,
        @RequestBody EndConsultingDto endConsultingDto) {

        Long consultingId = consultingService.endConsulting(endConsultingDto);
        Optional<Consulting> consulting = consultingRepository.findById(consultingId);
        return ResponseEntity.ok(endConsultingDto);
    }

    @GetMapping("/end/{consultingId}")
    public ResponseEntity<LocalTime> evaluateConsulting(@PathVariable Long consultingId,
        Model model) {

        LocalTime duration = consultingService.setConsultingEndInfoDto(consultingId);

        model.addAttribute("duration", duration);
        model.addAttribute("likeDetailDto", new LikeDetailDto());
        return ResponseEntity.ok(duration);
    }

    @PostMapping("/end/{consultingId}")
    public ResponseEntity<LikeDetailDto> finishAll(@PathVariable Long consultingId,
        @RequestBody LikeDetailDto likeDetailDto) {

        likeDetailService.createLikeDetail(consultingId, likeDetailDto);
        return ResponseEntity.ok(likeDetailDto);
    }


}
