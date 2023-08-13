package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.LikeDetail;
import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.dto.consulting.LikeDetailDto;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultingRepository;
import com.likelion.daroomi.nuroomi.repository.LikeDetailRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeDetailService {

    private final ConsultantRepository consultantRepository;
    private final ConsultingRepository consultingRepository;
    private final LikeDetailRepository likeDetailRepository;

    public Long createLikeDetail(Long consultingId, LikeDetailDto likeDetailDto) {

        LikeDetail likeDetail = new LikeDetail();

        Optional<Consulting> consulting = consultingRepository.findById(consultingId);

        Long consultantId = consulting.get().getConsultant().getId();
        Optional<Consultant> consultant = consultantRepository.findById(consultantId);

        likeDetail.setConsultant(consultant.get());
        likeDetail.setConsulting(consulting.get());
        likeDetail.setLikeOrDislike(likeDetailDto);

        return likeDetailRepository.save(likeDetail).getId();
    }
}
