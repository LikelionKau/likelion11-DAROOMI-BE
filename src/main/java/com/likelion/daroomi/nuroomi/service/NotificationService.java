package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.board.Notification;
import com.likelion.daroomi.nuroomi.dto.GetNotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.NotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.NotificationResponseDto;
import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> getNotificationList();

    Notification createNotification(NotificationRequestDto notificationRequestDto);

    NotificationResponseDto readNotificationById(
        GetNotificationRequestDto getNotificationRequestDto);

    NotificationResponseDto updateNotification(NotificationRequestDto notificationRequestDto);

    void deleteNotification(NotificationRequestDto notificationRequestDto);
}