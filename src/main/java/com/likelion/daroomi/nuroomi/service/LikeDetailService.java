package com.likelion.daroomi.nuroomi.service.detail;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.detail.LikeDetail;
import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import com.likelion.daroomi.nuroomi.dto.LikeDetailDto;
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

    public Long createLikeDetail(Long consultantId, Long consultingId, LikeDetailDto likeDetailDto) {

        LikeDetail likeDetail = new LikeDetail();

        Optional<Consultant> consultant = consultantRepository.findById(consultantId);
        Optional<Consulting> consulting = consultingRepository.findById(consultingId);

        likeDetail.setConsultant(consultant.get());
        likeDetail.setConsulting(consulting.get());
        likeDetail.setLikeOrDislike(likeDetailDto);

        likeDetailRepository.save(likeDetail);

        return likeDetail.getId();
    }
}
