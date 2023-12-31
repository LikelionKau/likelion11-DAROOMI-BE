package com.likelion.daroomi.nuroomi;

import com.likelion.daroomi.nuroomi.domain.board.Notification;
import com.likelion.daroomi.nuroomi.dto.GetNotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.NotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.NotificationResponseDto;
import com.likelion.daroomi.nuroomi.repository.NotificationRepository;
import com.likelion.daroomi.nuroomi.service.NotificationService;
import com.likelion.daroomi.nuroomi.service.NotificationServiceImpl;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class NotificationServiceTest {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationService notificationService;

    @Test
    @DisplayName("공지사항 목록 테스트")
    public void getNotificationListTest() {
        //given
        Notification notification1 = new Notification(new Timestamp(System.currentTimeMillis()),
            "content1", "title1");
        Notification notification2 = new Notification(new Timestamp(System.currentTimeMillis()),
            "content2", "title2");
        Notification notification3 = new Notification(new Timestamp(System.currentTimeMillis()),
            "content3", "title3");

        //when

        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);

        //then
        notificationService.getNotificationList();

        Assertions.assertThat(notification1.getId()).isEqualTo(1L);
        Assertions.assertThat(notification2.getId()).isEqualTo(2L);
        Assertions.assertThat(notification3.getId()).isEqualTo(3L);

    }

    @Test
    @DisplayName("공지사항 생성 테스트")
    public void createNotificationTest() {
        //given
        Notification notification = new Notification(new Timestamp(System.currentTimeMillis()),
            "content1", "title1");

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto(
            notification.getId(), "title1", "content1", notification.getCreatedDate());

        //when
        Notification savedNotification = notificationService.createNotification(
            notificationRequestDto);

        //then
        Assertions.assertThat(savedNotification.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("공지사항 게시물 열람 테스트")
    public void readNotificationTest() {
        Notification notification = new Notification(new Timestamp(System.currentTimeMillis()),
            "PLZ", "PLZ");
        notificationRepository.save(notification);

        Long findThatId = notification.getId();
        GetNotificationRequestDto getNotificationRequestDto = new GetNotificationRequestDto(
            findThatId);
        notificationService.readNotificationById(getNotificationRequestDto);

        Assertions.assertThat(notification.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    public void modifyNotificationTest() {
        //given
        Notification notification = new Notification(new Timestamp(System.currentTimeMillis()),
            "content1", "title1");

        notificationRepository.save(notification);
        Optional<Notification> newNotification = notificationRepository.findById(
            notification.getId());

        //when
        if (newNotification.isPresent()) {
            String newTitle = "new title";
            String newContent = "new content";
            Timestamp newTime = new Timestamp(System.currentTimeMillis());
            newNotification.get().updateNotification(newTitle, newContent, newTime);
        }
        //then
        Assertions.assertThat(newNotification.get().getId()).isEqualTo(1L);
        Assertions.assertThat(newNotification.get().getContent()).isEqualTo("new content");
    }


    @Test
    @DisplayName("공지사항 삭제 테스트")
    public void deleteNotificationTest() {
        //given
        Notification notification = new Notification(new Timestamp(System.currentTimeMillis()),
            "content1", "title1");

        Notification notification2 = new Notification(new Timestamp(System.currentTimeMillis()),
            "content2", "title2");

        notificationRepository.save(notification);
        notificationRepository.save(notification2);

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto(
            notification.getId(), "title1", "content1", notification.getCreatedDate());

        //when
        notificationService.deleteNotification(notificationRequestDto);

        //then
        Notification findId = notificationRepository.findById(notification.getId()).get();
        Assertions.assertThat(findId.getId()).isEqualTo(null);

        //  Notification findId2 = notificationRepository.findById(notification2.getId()).get();
        //  Assertions.assertThat(notificationRepository.findById(notification2.getId())).isEqualTo(2L);
    }

}