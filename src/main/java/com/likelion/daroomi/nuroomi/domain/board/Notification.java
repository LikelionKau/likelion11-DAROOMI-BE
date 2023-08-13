package com.likelion.daroomi.nuroomi.domain.board;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "notification_id"))
@SuperBuilder
@Getter
public class Notification extends Board {

    @NotNull
    @Column(length = 50)
    private String title;

    public Notification(Timestamp createdTime, String content, String title) {
        this.setBoard(createdTime, content);
        this.title = title;
    }

    public void updateNotification(String title, String content, Timestamp createdDate) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

}
