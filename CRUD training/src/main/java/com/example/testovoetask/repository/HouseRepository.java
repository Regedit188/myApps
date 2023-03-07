package com.example.testovoetask.repository;

import com.example.testovoetask.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {

}
