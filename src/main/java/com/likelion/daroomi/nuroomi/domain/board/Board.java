package com.likelion.daroomi.nuroomi.domain.board;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Timestamp createdDate;

    @NotNull
    @Lob
    private String content;

}
