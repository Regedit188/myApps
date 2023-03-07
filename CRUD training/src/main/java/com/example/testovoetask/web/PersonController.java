package com.example.testovoetask.web;

import com.example.testovoetask.api.PersonRequestApi;
import com.example.testovoetask.api.PersonResponseApi;
import com.example.testovoetask.mappers.PersonMapper;
import com.example.testovoetask.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @GetMapping(path = "/api/person/{id}")
    public PersonResponseApi getSinglePerson(@PathVariable Long id) {
        return personMapper.toResponseApi(personService.getSinglePerson(id));
    }

    @GetMapping(path = "/api/person")
    public List<PersonResponseApi> getAllCitizens() {
        return personService.getAllCitizens()
            .stream()
            .map(personMapper::toResponseApi)
            .toList();
    }

    @GetMapping(path = "/api/person/house/{street}")
    public List<PersonResponseApi> getAllCitizensOnTheStreet(@PathVariable String street) {
        return personService.getAllCitizensOnTheStreet(street)
            .stream()
            .map(personMapper::toResponseApi)
            .toList();
    }

    @PostMapping(path = "/api/person/create")
    public PersonResponseApi createPerson(@RequestBody PersonRequestApi personRequestApi) {
        return personMapper.toResponseApi(personService.createPerson(personRequestApi));
    }

    @PutMapping(path = "/api/person/{id}")
    public PersonResponseApi updatePerson(@PathVariable Long id, @RequestBody @Valid PersonRequestApi personUpdateApi) {
        return personMapper.toResponseApi(personService.updatePerson(id, personUpdateApi));
    }

    @DeleteMapping(path = "/api/person/{id}/delete")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    @PostMapping("/api/person/{personId}/car/{carId}/buy")
    public void buyCar(@PathVariable Long personId, @PathVariable Long carId) {
        personService.buyCar(personId, carId);
    }

    @PostMapping("/api/person/{personId}/house/{houseId}/buy")
    public void buyHouse(@PathVariable Long personId, @PathVariable Long houseId) {
        personService.buyHouse(personId, houseId);
    }
}
