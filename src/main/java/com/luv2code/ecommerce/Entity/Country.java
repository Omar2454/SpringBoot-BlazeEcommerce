package com.luv2code.ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "country", schema = "full-stack-ecommerce")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "smallint UNSIGNED not null")
    private Integer id;

    @Column(name = "code", length = 2)
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "country")
    private Set<State> states = new LinkedHashSet<>();

}
