package com.likelion.daroomi.nuroomi.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consultantee")
@AttributeOverride(name = "id", column = @Column(name = "consultantee_id"))
@NoArgsConstructor
public class Consultantee extends AllUser {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;

    @OneToMany(mappedBy = "consultantee")
    private List<Consulting> consultings = new ArrayList<>();

    public Consultantee(String loginId, String password) {
        this.setUser(loginId, password);
    }
}
