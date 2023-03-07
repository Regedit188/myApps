package com.example.testovoetask.entities;

import com.example.testovoetask.enums.HouseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "houses")
@Getter
@Setter
@Accessors(chain = true)
public class House {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long houseId;

    private int countOfFloors;

    private HouseType houseType;

    private String street;

    @ManyToMany
    @JoinTable (name="citizensHouses",
            joinColumns=@JoinColumn (name="personId"),
            inverseJoinColumns=@JoinColumn(name="houseId"))
    private List<Person> citizens;
}
