package com.likelion.daroomi.nuroomi.domain;

import com.likelion.daroomi.nuroomi.dto.LikeDetailDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "like_detail")
@Getter
public class LikeDetail {

    @Id
    @Column(name = "like_detail_id")
    private Long id;

    @ColumnDefault("false")
    private Boolean likes = false;

    @ColumnDefault("false")
    private Boolean dislikes = false;

    @Column(length = 50)
    //todo enum or various?
    private String reason;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id")
    private Consulting consulting;

    //연관관계 메서드
    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
        consultant.getLikeDetails().add(this);
    }

    public void setConsulting(Consulting consulting) {
        this.consulting = consulting;
        consulting.setLikeDetail(this);
    }

    public void setLikeOrDislike(LikeDetailDto likeDetailDto) {
        this.likes = likeDetailDto.getLikes();
        this.dislikes = likeDetailDto.getDislikes();
        this.reason = likeDetailDto.getReason();
    }
}
