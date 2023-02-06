package com.project.mypocketpurse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "actions")
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "actions_seq", allocationSize = 1)
public class Action
        extends GenericModel {

    @Column(name = "amount")
    private Double amount;

    @Column(name = "action_type")
    @Enumerated
    private ActionType actionType;

    @Column(name = "date_time")
    private LocalDate dateTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "label_id")
    @JsonIgnore
    private Label label;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account account;
}