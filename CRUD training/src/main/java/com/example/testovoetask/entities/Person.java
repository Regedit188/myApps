package com.example.testovoetask.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long personId;

    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="passportId")
    @JsonManagedReference
    private Passport passport;

    @ManyToMany
    @JoinTable (name="citizensHouses",
            joinColumns=@JoinColumn (name="houseId"),
            inverseJoinColumns=@JoinColumn(name="personId"))
    private List<House> houses;

    @OneToMany (mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Car> cars;
}
