package com.likelion.daroomi.nuroomi.domain.detail;

import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "like_detail")
public class LikeDetail {

    @Id
    @Column(name = "like_detail_id")
    private Long id;

    @ColumnDefault("false")
    private Boolean likes;

    @ColumnDefault("false")
    private Boolean dislikes;

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
}
