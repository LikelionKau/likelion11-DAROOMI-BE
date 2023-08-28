package com.likelion.daroomi.nuroomi.dto.consulting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JoinWaitingListDto {
    private Long userId;
    private Boolean isConsultant;
}
