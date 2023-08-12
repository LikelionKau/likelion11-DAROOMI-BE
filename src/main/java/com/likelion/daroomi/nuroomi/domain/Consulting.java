package com.likelion.daroomi.nuroomi.domain;

import com.likelion.daroomi.nuroomi.domain.detail.LikeDetail;
import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import com.likelion.daroomi.nuroomi.domain.user.Consultantee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import lombok.Getter;

@Entity
@Table(name = "consulting")
@Getter
public class Consulting {

    @Id
    @Column(name = "consulting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Timestamp createdTime;

    //@NotNull
    private LocalTime duration;

    //@NotNull
    private String voiceUrl;

    private String videoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultantee_id")
    private Consultantee consultantee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "consulting")
    private LikeDetail likeDetail;

    public void setLikeDetail(LikeDetail likeDetail) {
        this.likeDetail = likeDetail;
    }

    public static Consulting initConsulting(Consultant consultant, Consultantee consultantee) {
        Consulting consulting = new Consulting();
        consulting.consultant = consultant;
        consulting.consultantee = consultantee;
        consulting.createdTime = new Timestamp(System.currentTimeMillis());

        return consulting;
    }

    public Consulting createConsulting(String voiceUrl, String videoUrl) {
        this.duration = calculateDuration();
        this.voiceUrl = voiceUrl;
        if (videoUrl != null) {
            this.videoUrl = videoUrl;
        }

        return this;
    }

    private LocalTime calculateDuration() {
        Timestamp startTime = createdTime;
        Timestamp endTime = new Timestamp(System.currentTimeMillis());

        long timeGap = endTime.getTime() - startTime.getTime();
        Duration duration = Duration.ofMillis(timeGap);

        return LocalTime.ofSecondOfDay(duration.getSeconds());
    }

}