package com.likelion.daroomi.nuroomi.dto.Notification;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationRequestDto {

    private Long id;
    private String title;
    private String content;

}
