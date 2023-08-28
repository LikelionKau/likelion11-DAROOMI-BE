package com.likelion.daroomi.nuroomi.repository;

import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import com.likelion.daroomi.nuroomi.domain.user.Consultantee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {

    Optional<Consultant> findByLoginIdAndPassword(String loginId, String password);

    Optional<Consultant> findByLoginId(String loginId);
}
