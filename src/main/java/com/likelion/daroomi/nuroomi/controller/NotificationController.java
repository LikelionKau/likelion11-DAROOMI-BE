package com.likelion.daroomi.nuroomi.controller;

import com.likelion.daroomi.nuroomi.dto.Notification.GetNotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.Notification.NotificationRequestDto;
import com.likelion.daroomi.nuroomi.dto.Notification.NotificationResponseDto;
import com.likelion.daroomi.nuroomi.service.NotificationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("")
    public ResponseEntity<List<NotificationResponseDto>> getAllNotification() {
        List<NotificationResponseDto> notificationResponseDtoList = notificationService.getNotificationList();
        return ResponseEntity.ok(notificationResponseDtoList);
    }

    @GetMapping("/getNotification")
    public ResponseEntity<NotificationResponseDto> getNotification(@RequestParam Long id) {
        GetNotificationRequestDto getNotificationRequestDto = new GetNotificationRequestDto(id);
        NotificationResponseDto notificationResponseDto =  notificationService.readNotificationById(getNotificationRequestDto);
        return ResponseEntity.ok(notificationResponseDto);
    }

    @PostMapping("/write")
    public ResponseEntity<NotificationResponseDto> createNotification(@RequestBody
        NotificationRequestDto notificationRequestDto) {
        NotificationResponseDto notificationResponseDto = notificationService.createNotification(notificationRequestDto);
        return ResponseEntity.ok(notificationResponseDto);
    }

    @PutMapping("/modify")
    public ResponseEntity<NotificationResponseDto> updateNotification(@RequestBody NotificationRequestDto notificationRequestDto) {
        NotificationResponseDto notificationResponseDto = notificationService.updateNotification(notificationRequestDto);
        return ResponseEntity.ok(notificationResponseDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<NotificationResponseDto> deleteNotification(@RequestBody NotificationRequestDto notificationRequestDto) {
        NotificationResponseDto notificationResponseDto = notificationService.deleteNotification(notificationRequestDto);
        return ResponseEntity.ok(notificationResponseDto);
    }


}
