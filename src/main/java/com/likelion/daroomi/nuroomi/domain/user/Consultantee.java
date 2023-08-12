package com.likelion.daroomi.nuroomi.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.daroomi.nuroomi.domain.AllUser;
import com.likelion.daroomi.nuroomi.domain.Consulting;
import com.likelion.daroomi.nuroomi.domain.Role;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "consultantee")
@AttributeOverride(name = "id", column = @Column(name = "consultantee_id"))
public class Consultantee extends AllUser {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Role role;

    @OneToMany(mappedBy = "consultantee")
    @JsonIgnore
    private List<Consulting> consultings = new ArrayList<>();
}
