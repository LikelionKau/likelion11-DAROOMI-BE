package com.likelion.daroomi.nuroomi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.daroomi.nuroomi.domain.Address;
import com.likelion.daroomi.nuroomi.domain.AllUser;
import com.likelion.daroomi.nuroomi.domain.Application;
import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.LikeDetail;
import com.likelion.daroomi.nuroomi.domain.PointUseDetail;
import com.likelion.daroomi.nuroomi.exception.NegativeTotalPointException;
import com.likelion.daroomi.nuroomi.domain.board.Answer;
import com.likelion.daroomi.nuroomi.domain.Application;
import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.board.Question;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@AttributeOverride(name = "id", column = @Column(name = "consultant_id"))
@Table(name = "consultant")
@NoArgsConstructor
public class Consultant extends AllUser {

    @NotNull
    private String profileImage;

    @NotNull
    @ColumnDefault("false")
    private Boolean isAccepted = false;

    public Consultant(String loginId, String password, String profileImage, Boolean isAccepted) {
        this.setUser(loginId, password);
        this.profileImage = profileImage;
        this.isAccepted = isAccepted;
    }

    @ColumnDefault("0")
    private Integer point = 0;

    @Column(length = 15)
    private String bankCompany;

    @Column(length = 15)
    private String bankAccount;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consultant")
    private Application application;

    @JsonIgnore
    @OneToMany(mappedBy = "consultant")
    private List<Consulting> consultings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consultant")
    private List<PointUseDetail> pointUseDetails = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consultant")
    private List<LikeDetail> likeDetails = new ArrayList<>();

    public void addPoint(int addPoint) {
        this.point += addPoint;
    }

    public void modifyInfo(String profileImage, Address address, String phoneNumber,
        String email, String bankCompany, String bankAccount) {
        this.profileImage = profileImage;
        this.bankCompany = bankCompany;
        this.bankAccount = bankAccount;
        modifyInfo(address, phoneNumber, email);
    }

    public void modifyTotalPoint(Integer difference) {
        int newTotalPoint = this.point + difference;
        if (newTotalPoint < 0) {
            throw new NegativeTotalPointException("Total point cannot be negative");
        }
        this.point = newTotalPoint;
    }

    @OneToMany(mappedBy = "consultant")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "consultant")
    private List<Answer> answers = new ArrayList<>();
}
