package com.likelion.daroomi.nuroomi.domain;

import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "point_use_detail")
public class PointUseDetail {

    @Id
    @Column(name = "point_use_detail_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ColumnDefault("0")
    private Integer usePoint = 0;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Options options;

    @NotNull
    private Timestamp createTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;
}
