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
@Table(name = "labels")
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "labels_seq", allocationSize = 1)
public class Label
        extends GenericModel {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "label", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @JsonIgnore
    private Set<Action> actions = new LinkedHashSet<>();

}
