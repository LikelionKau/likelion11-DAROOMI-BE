package com.likelion.daroomi.nuroomi.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "application")
public class Application {

    @Id
    @Column(name = "consultant_id")
    private Long id;

    @NotNull
    @Lob
    @Column(name = "q1")
    private String question1;

    @NotNull
    @Lob
    @Column(name = "q2")
    private String question2;

    @NotNull
    @Lob
    @Column(name = "q3")
    private String question3;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    public void modifyInfo(String question1, String question2, String question3) {
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
    }
}
