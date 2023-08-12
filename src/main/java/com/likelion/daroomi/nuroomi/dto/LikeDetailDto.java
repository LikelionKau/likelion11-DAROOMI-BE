package com.likelion.daroomi.nuroomi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDetailDto {
    private Boolean likes;
    private Boolean dislikes;
    private String reason;
}
