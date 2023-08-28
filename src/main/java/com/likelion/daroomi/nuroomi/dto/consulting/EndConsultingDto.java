package com.likelion.daroomi.nuroomi.dto.consulting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndConsultingDto {
    private Long consultingId;
    private String voiceUrl;
    private String videoUrl;
}
