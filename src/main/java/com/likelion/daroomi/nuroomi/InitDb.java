package com.likelion.daroomi.nuroomi;

import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.domain.Consultantee;
import com.likelion.daroomi.nuroomi.domain.board.Notification;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultanteeRepository;
import com.likelion.daroomi.nuroomi.repository.NotificationRepository;
import jakarta.annotation.PostConstruct;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initDb();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final ConsultantRepository consultantRepository;
        private final ConsultanteeRepository consultanteeRepository;
        private final NotificationRepository notificationRepository;

        public void initDb() {
            Consultant consultant = new Consultant("consultantId", "consultantPw",
                "consultantProfile", true);
            consultantRepository.save(consultant);

            Consultantee consultantee = new Consultantee("consultanteeId", "consultanteePw");
            consultanteeRepository.save(consultantee);

            //notification temp data
            Notification notification1 = new Notification(1L, new Timestamp(System.currentTimeMillis()),
                "content1", "title1");
            Notification notification2 = new Notification(2L, new Timestamp(System.currentTimeMillis()),
                "content2", "title2");
            notificationRepository.save(notification1);
            notificationRepository.save(notification2);


        }
    }

}
