package com.likelion.daroomi.nuroomi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(length = 5)
    private String zipCode;

    @Column(length = 50)
    private String addressBase;

    @Column(length = 50)
    private String addressDetail;
}
