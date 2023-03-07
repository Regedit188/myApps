package com.example.testovoetask.mappers;

import com.example.testovoetask.api.HouseResponseApi;
import com.example.testovoetask.entities.House;
import com.example.testovoetask.entities.Person;
import org.springframework.stereotype.Component;

@Component
public class HouseMapper {

    public HouseResponseApi toResponseApi(House house) {
        return new HouseResponseApi()
            .setHouseId(house.getHouseId())
            .setCountOfFloors(house.getCountOfFloors())
            .setHouseType(house.getHouseType())
            .setStreet(house.getStreet())
            .setOwnersIds(house.getCitizens() == null ? null : house.getCitizens().stream()
                .map(Person::getPersonId)
                .toList());
    }
}
