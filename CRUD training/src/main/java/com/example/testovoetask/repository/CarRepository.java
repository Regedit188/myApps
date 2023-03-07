package com.example.testovoetask.repository;

import com.example.testovoetask.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllCarsByUserId(Long id);
}
