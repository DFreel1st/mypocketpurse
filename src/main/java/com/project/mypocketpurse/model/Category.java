package com.project.mypocketpurse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "categories_seq", allocationSize = 1)
public class Category
        extends GenericModel {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<Action> actions;
}
