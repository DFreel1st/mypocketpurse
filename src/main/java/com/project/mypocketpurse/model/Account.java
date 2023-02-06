package com.project.mypocketpurse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "accounts_seq", allocationSize = 1)
public class Account
        extends GenericModel {
    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "currency_type")
    @Enumerated
    private CurrencyType currencyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ACCOUNT_USER"), nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<Action> actions = new LinkedHashSet<>();
}
