package com.likelion.daroomi.nuroomi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @Column(name = "consultant_id")
    private Long consultantId;

    @NotNull
    @Column(name = "q1")
    private String question1;

    @NotNull
    @Column(name = "q2")
    private String question2;

    @NotNull
    @Column(name = "q3")
    private String question3;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

}
