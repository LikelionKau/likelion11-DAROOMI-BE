package com.likelion.daroomi.nuroomi.domain.board;

import com.likelion.daroomi.nuroomi.domain.board.Board;
import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import com.likelion.daroomi.nuroomi.domain.user.Consultantee;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@SuperBuilder
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "question_id"))
@Getter
public class Question extends Board {

    @NotNull
    private Boolean isUser;

    @NotNull
    @Column(length = 50)
    private String title;

    @NotNull
    @ColumnDefault("0")
    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_consultant_id")
    private Consultant consultant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_consultantee_id")
    private Consultantee consultantee;

}
