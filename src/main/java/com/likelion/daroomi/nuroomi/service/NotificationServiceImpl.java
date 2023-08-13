package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.board.Notification;
import com.likelion.daroomi.nuroomi.dto.GetNotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.NotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.NotificationResponseDto;
import com.likelion.daroomi.nuroomi.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    // 공지사항 목록 조회
    @Override
    public List<NotificationResponseDto> getNotificationList() {
        try {
            List<Notification> notifications = notificationRepository.findAll();
            List<NotificationResponseDto> notificationResponseDtoList = new ArrayList<>();

            for (Notification notification : notifications) {
                NotificationResponseDto responseDto = NotificationResponseDto.builder()
                    .title(notification.getTitle())
                    .content(notification.getContent())
                    .createdDate(notification.getCreatedDate())
                    .build();

                notificationResponseDtoList.add(responseDto);
            }
            return notificationResponseDtoList;

        } catch (Exception e) {
            /*
            예외 처리
             */
        }
        return null;
    }

    // 공지사항 작성
    @Override
    @Transactional
    public Notification createNotification(
        NotificationRequestDto notificationRequestDto) {
        Notification newNotification = Notification.builder()
            .title(notificationRequestDto.getTitle())
            .content(notificationRequestDto.getContent())
            .createdDate(notificationRequestDto.getCreatedDate())
            .build();

        Notification notification = notificationRepository.save(newNotification);

        return notification;
    }

    // 공지사항 게시물 열람
    @Override
    @Transactional
    public NotificationResponseDto readNotificationById(
        GetNotificationRequestDto getNotificationRequestDto) {
        Optional<Notification> notification = notificationRepository.findById(
            getNotificationRequestDto.getId());
        return NotificationResponseDto.builder()
            .title(notification.get().getTitle())
            .content(notification.get().getContent())
            .createdDate(notification.get().getCreatedDate())
            .build();
    }

    // 공지사항 수정
    @Override
    @Transactional
    public NotificationResponseDto updateNotification(
        NotificationRequestDto notificationRequestDto) {
        Optional<Notification> optionalNotification = notificationRepository.findById(
            notificationRequestDto.getId());

        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();

            notification.updateNotification(
                notificationRequestDto.getTitle(),
                notificationRequestDto.getContent(),
                notificationRequestDto.getCreatedDate());

            Notification updatedNotification = notificationRepository.save(notification);

            return NotificationResponseDto.builder()
                .title(updatedNotification.getTitle())
                .content(updatedNotification.getContent())
                .createdDate(updatedNotification.getCreatedDate())
                .build();
        } else {
            throw new RuntimeException();
        }
    }

    public void deleteNotification(NotificationRequestDto notificationRequestDto) {
        Optional<Notification> optionalNotification = notificationRepository.findById(
            notificationRequestDto.getId());

        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notificationRepository.delete(notification);
        }
    }

}

