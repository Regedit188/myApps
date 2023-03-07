package com.example.testovoetask.repository;

import com.example.testovoetask.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p join p.houses h where h.street = :street")
    List<Person> findAllCitizensByStreet(String street);

}
