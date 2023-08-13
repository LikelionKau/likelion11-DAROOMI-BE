package com.likelion.daroomi.nuroomi.repository;

import com.likelion.daroomi.nuroomi.domain.PointUseDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointUseDetailRepository extends JpaRepository<PointUseDetail, Long> {

    @Query("SELECT p FROM PointUseDetail p WHERE p.consultant.id=:id")
    List<PointUseDetail> findByConsultantId(@Param("id") Long id);
}
