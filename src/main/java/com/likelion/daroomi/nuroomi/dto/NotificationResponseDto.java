package com.likelion.daroomi.nuroomi.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NotificationResponseDto {

    private String title;
    private String content;
    private Timestamp createdDate;

}
