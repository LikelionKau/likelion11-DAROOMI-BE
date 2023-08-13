package com.likelion.daroomi.nuroomi.domain;

import com.likelion.daroomi.nuroomi.domain.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class AllUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = 50)
    private String loginId;

    @NotNull
    @Column(length = 50)
    private String password;

    public void setUser(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

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

        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void modifyPassword(ChangePasswordRequestDto changePasswordRequestDto) {
        this.password = changePasswordRequestDto.getPassword();
    }
}

