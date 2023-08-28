package com.likelion.daroomi.nuroomi.service;

import com.likelion.daroomi.nuroomi.domain.board.Notification;
import com.likelion.daroomi.nuroomi.dto.Notification.GetNotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.Notification.NotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.Notification.NotificationResponseDto;
import com.likelion.daroomi.nuroomi.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
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
    public NotificationResponseDto createNotification(
        NotificationRequestDto notificationRequestDto) {

        Notification newNotification = Notification.builder()
            .title(notificationRequestDto.getTitle())
            .content(notificationRequestDto.getContent())
            .createdDate(new Timestamp(System.currentTimeMillis()))
            .build();

        Notification notification = notificationRepository.save(newNotification);

        return NotificationResponseDto.builder()
            .title(notification.getTitle())
            .content(notification.getContent())
            .createdDate(notification.getCreatedDate())
            .build();
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
                notificationRequestDto.getContent());

            Notification updatedNotification = notificationRepository.save(notification);

            return NotificationResponseDto.builder()
                .title(updatedNotification.getTitle())
                .content(updatedNotification.getContent())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .build();
        } else {
            throw new RuntimeException();
        }
    }

    public NotificationResponseDto deleteNotification(NotificationRequestDto notificationRequestDto) {
        Optional<Notification> optionalNotification = notificationRepository.findById(
            notificationRequestDto.getId());

        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notificationRepository.delgiete(notification);

            return NotificationResponseDto.builder()
                .title(notification.getTitle())
                .content(notification.getContent())
                .createdDate(notification.getCreatedDate())
                .build();

        }
        return null;
    }

}

