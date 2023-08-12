package com.likelion.daroomi.nuroomi.domain.board;

import com.likelion.daroomi.nuroomi.domain.user.Consultant;
import com.likelion.daroomi.nuroomi.domain.user.Consultantee;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "answer_id"))
public class Answer extends Board {

    @NotNull
    private Boolean isUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_consultant_id")
    private Consultant consultant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_consultantee_id")
    private Consultantee consultantee;

    // 대댓글을 위한 자기자신 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Answer parent;

    @OneToMany(mappedBy = "parent")
    private List<Answer> parent_id = new ArrayList<>();


}
