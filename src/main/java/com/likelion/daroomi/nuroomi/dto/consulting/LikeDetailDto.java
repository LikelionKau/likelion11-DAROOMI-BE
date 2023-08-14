package com.likelion.daroomi.nuroomi.dto.consulting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDetailDto {
    private Boolean likes;
    private Boolean dislikes;
    private String reason;
}
