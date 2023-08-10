package com.likelion.daroomi.nuroomi.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "consultantee")
@AttributeOverride(name = "userId", column = @Column(name = "consultantee_id"))
public class Consultantee extends AllUser {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;
}
