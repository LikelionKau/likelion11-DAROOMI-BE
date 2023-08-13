package com.likelion.daroomi.nuroomi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.likelion.daroomi.nuroomi.repository.ConsultantRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ConsultantTest {
    @Autowired
    ConsultantRepository consultantRepository;

    @Test
    @DisplayName("consultant 속성 조회")
    public void consultantTest() throws Exception {
        //given
        Consultant consultant = new Consultant("1234", "123", "profile", true);

        Consultant savedConsultant = consultantRepository.save(consultant);

        //when
        Optional<Consultant> findConsultant = consultantRepository.findById(savedConsultant.getId());

        //then
        assertThat(findConsultant.get().getLoginId()).isEqualTo("1234");
        assertThat(findConsultant.get().getPassword()).isEqualTo("123");
        assertThat(findConsultant.get().getProfileImage()).isEqualTo("profile");
        assertThat(findConsultant.get().getIsAccepted()).isEqualTo(false);
    }
}
