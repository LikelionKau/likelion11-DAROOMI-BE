package com.likelion.daroomi.nuroomi.domain.board;

import com.likelion.daroomi.nuroomi.domain.board.Board;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "notification_id"))
public class Notification extends Board {

    @NotNull
    @Column(length = 50)
    private String title;

}
