package com.likelion.daroomi.nuroomi.domain;

import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "like_detail")
public class LikeDetail {

    @Id
    @Column(name = "like_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean likes;

    private Boolean dislikes;

    @Column(length = 50)
    private String reason;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id")
    private Consulting consulting;
}
