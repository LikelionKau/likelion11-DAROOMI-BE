package com.likelion.daroomi.nuroomi.domain;

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
@AttributeOverride(name = "id", column = @Column(name = "consultant_id"))
@Table(name = "consultant")
@NoArgsConstructor
@Getter
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consultant")
    private Application application;

    @OneToMany(mappedBy = "consultant")
    private List<Consulting> consultings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consultant")
    private List<PointUseDetail> pointUseDetails = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consultant")
    private List<LikeDetail> likeDetails = new ArrayList<>();

    public void addPoint(int addPoint) {
        this.point += addPoint;
    }
}
