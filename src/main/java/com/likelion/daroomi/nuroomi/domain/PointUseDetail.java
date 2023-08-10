package com.likelion.daroomi.nuroomi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "point_use_detail")
public class PointUseDetail {

    @Id
    @Column(name = "point_use_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int usePoint = 0;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Options options;

    @NotNull
    private Timestamp createTime;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;
}
