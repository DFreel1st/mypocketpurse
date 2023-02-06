package com.project.mypocketpurse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "users_seq", allocationSize = 1)
public class User
        extends GenericModel {

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USER_ROLES"), nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Role roleId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<Account> accounts = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<Label> labels = new LinkedHashSet<>();
}
