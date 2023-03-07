package com.example.testovoetask.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PersonResponseApi {

    private Long personId;

    private String name;

    private String surname;

    private int age;

    private List<HouseResponseApi> houses;

    private List<CarResponseApi> cars;
}
