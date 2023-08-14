package com.likelion.daroomi.nuroomi.repository;

import com.likelion.daroomi.nuroomi.domain.Consultantee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultanteeRepository extends JpaRepository<Consultantee, Long> {

    Optional<Consultantee> findByLoginIdAndPassword(String loginId, String password);
}
