package com.example.testovoetask.entities;

import com.example.testovoetask.enums.Color;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@NamedQueries(
    @NamedQuery(name = "Car.findAllCarsByUserId", query = "from Car c where c.person.personId = :id")
)
public class Car {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long carId;

    private Color color;

    private String model;

    @ManyToOne
    @JoinColumn(name = "personId")
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Person person;
}
