package com.likelion.daroomi.nuroomi;

import com.likelion.daroomi.nuroomi.domain.Consultant;
import com.likelion.daroomi.nuroomi.domain.Consultantee;
import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import com.likelion.daroomi.nuroomi.repository.ConsultanteeRepository;
import jakarta.annotation.PostConstruct;
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

        public void initDb() {
            Consultant consultant = new Consultant("consultantId", "consultantPw",
                "consultantProfile", true);
            consultantRepository.save(consultant);

            Consultantee consultantee = new Consultantee("consultanteeId", "consultanteePw");
            consultanteeRepository.save(consultantee);
        }
    }

}
