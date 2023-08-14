package com.likelion.daroomi.nuroomi.controller;

import com.likelion.daroomi.nuroomi.dto.NotificationResponseDto;
import com.likelion.daroomi.nuroomi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/get")
    public ResponseEntity<NotificationResponseDto> getNotification() {
        
    }


}
