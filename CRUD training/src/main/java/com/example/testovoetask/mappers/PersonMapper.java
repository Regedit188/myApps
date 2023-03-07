package com.example.testovoetask.mappers;

import com.example.testovoetask.api.PersonResponseApi;
import com.example.testovoetask.entities.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final CarMapper carMapper;
    private final HouseMapper houseMapper;

    public PersonResponseApi toResponseApi(Person person) {
        return new PersonResponseApi()
            .setName(person.getPassport().getName())
            .setSurname(person.getPassport().getSurname())
            .setAge(person.getPassport().getAge())
            .setPersonId(person.getPersonId())
            .setHouses(person.getHouses() == null ? null : person.getHouses().stream()
                .map(houseMapper::toResponseApi)
                .toList())
            .setCars(person.getCars() == null ? null : person.getCars().stream()
                 .map(carMapper::toResponseApi)
                 .toList());
    }
}
