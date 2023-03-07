package com.example.testovoetask.repository;

import com.example.testovoetask.entities.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassportRepository extends JpaRepository<Passport, Long> {

    @Query(value = "select * from passports where surname like CONCAT(?1, '%')", nativeQuery = true)
    List<Passport> findAllPassportsBySurnameLetter(char l);
}
