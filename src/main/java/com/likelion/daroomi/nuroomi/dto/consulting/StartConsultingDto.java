package com.likelion.daroomi.nuroomi.dto.consulting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StartConsultingDto {
    private Long consultingId;
    private Long consultantId;
    private Long consultanteeId;

    public StartConsultingDto(Long consultantId, Long consultanteeId) {
        this.consultantId = consultantId;
        this.consultanteeId = consultanteeId;
    }
}
