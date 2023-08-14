package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.board.Notification;
import com.likelion.daroomi.nuroomi.dto.Notification.GetNotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.Notification.NotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.Notification.NotificationResponseDto;
import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> getNotificationList();

    NotificationResponseDto createNotification(NotificationRequestDto notificationRequestDto);

    NotificationResponseDto readNotificationById(
        GetNotificationRequestDto getNotificationRequestDto);

    NotificationResponseDto updateNotification(NotificationRequestDto notificationRequestDto);

    NotificationResponseDto deleteNotification(NotificationRequestDto notificationRequestDto);
}