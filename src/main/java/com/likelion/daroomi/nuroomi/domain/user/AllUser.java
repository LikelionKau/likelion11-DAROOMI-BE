package com.likelion.daroomi.nuroomi.domain.user;

import com.likelion.daroomi.nuroomi.domain.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AllUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50)
    private String loginId;

    @NotNull
    @Column(length = 50)
    private String password;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String name;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 7)
    private String socialNumber;

    @Embedded
    private Address address;
}

