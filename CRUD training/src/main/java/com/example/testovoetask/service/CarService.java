package com.example.testovoetask.service;

import com.example.testovoetask.api.CarRequestApi;
import com.example.testovoetask.entities.Car;
import com.example.testovoetask.enums.CommonErrorCode;
import com.example.testovoetask.exception.CustomException;
import com.example.testovoetask.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

   public Car getSingleCar(Long id) {
       return getCarSafe(id);
   }

   public List<Car> getAllPersonCars(Long personId) {
       return carRepository.findAllCarsByUserId(personId);
   }

   public Car createCar(CarRequestApi carRequestApi) {
       return carRepository.save(new Car()
           .setColor(carRequestApi.getColor())
           .setModel(carRequestApi.getModel()));
   }

   public void deleteCar(Long id) {
       carRepository.deleteById(id);
   }

   public Car updateCar(Long id, CarRequestApi carRequestApi) {
       return carRepository.save(getCarSafe(id)
           .setColor(carRequestApi.getColor())
           .setModel(carRequestApi.getModel()));
   }

   private Car getCarSafe(Long id) {
       return carRepository.findById(id)
           .orElseThrow(() -> new CustomException(CommonErrorCode.ENTITY_NOT_FOUND));
   }
}
