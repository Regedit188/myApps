package com.example.testovoetask.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Entity
@Table(name = "passports")
@Getter
@Setter
@Accessors(chain = true)
public class Passport {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long passportId;

    private String name;

    private String surname;

    private int age;

    @OneToOne(mappedBy = "passport")
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Person owner;
}
