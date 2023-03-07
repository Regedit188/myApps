package com.example.testovoetask.web;

import com.example.testovoetask.api.CarRequestApi;
import com.example.testovoetask.api.CarResponseApi;
import com.example.testovoetask.mappers.CarMapper;
import com.example.testovoetask.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarService carService;

    private final CarMapper carMapper;

    @GetMapping(path = "/api/cars/{id}")
    public CarResponseApi getSingleCar(@PathVariable Long id) {
        return carMapper.toResponseApi(carService.getSingleCar(id));
    }

    @GetMapping(path = "/api/cars/person/{id}")
    public List<CarResponseApi> getAllPersonCars(@PathVariable Long id) {
        return carService.getAllPersonCars(id).stream()
            .map(carMapper::toResponseApi)
            .toList();
    }

    @PostMapping(path = "/api/cars/create")
    public CarResponseApi createCar(@RequestBody @Valid CarRequestApi carRequestApi) {
        return carMapper.toResponseApi(carService.createCar(carRequestApi));
    }

    @DeleteMapping(path = "/api/cars/{id}/delete")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @PutMapping(path = "/api/cars/{id}/update")
    public CarResponseApi updateCar(@PathVariable Long id, @RequestBody @Valid CarRequestApi carRequestApi) {
        return carMapper.toResponseApi(carService.updateCar(id, carRequestApi));
    }
}
