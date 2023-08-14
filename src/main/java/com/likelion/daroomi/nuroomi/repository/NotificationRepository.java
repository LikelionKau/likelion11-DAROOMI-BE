package com.likelion.daroomi.nuroomi.repository;

import com.likelion.daroomi.nuroomi.domain.board.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
