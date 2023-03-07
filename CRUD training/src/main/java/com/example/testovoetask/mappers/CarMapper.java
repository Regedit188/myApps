package com.example.testovoetask.mappers;

import com.example.testovoetask.api.CarResponseApi;
import com.example.testovoetask.entities.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarResponseApi toResponseApi(Car car) {
        return new CarResponseApi()
            .setCarId(car.getCarId())
            .setColor(car.getColor())
            .setModel(car.getModel())
            .setOwnerId(car.getPerson() == null ? null : car.getPerson().getPersonId());
    }
}
