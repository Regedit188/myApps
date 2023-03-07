package com.example.testovoetask.service;

import com.example.testovoetask.api.PersonRequestApi;
import com.example.testovoetask.entities.Car;
import com.example.testovoetask.entities.House;
import com.example.testovoetask.entities.Passport;
import com.example.testovoetask.entities.Person;
import com.example.testovoetask.enums.CommonErrorCode;
import com.example.testovoetask.exception.CustomException;
import com.example.testovoetask.repository.CarRepository;
import com.example.testovoetask.repository.HouseRepository;
import com.example.testovoetask.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;
    private final HouseRepository houseRepository;

    public Person getSinglePerson(Long id) {
        return findPersonSafe(id);
    }

    public List<Person> getAllCitizens() {
        return personRepository.findAll();
    }

    public List<Person> getAllCitizensOnTheStreet(String street) {
        return personRepository.findAllCitizensByStreet(street);
    }

    public Person createPerson(PersonRequestApi personRequestApi) {
        Passport passport = new Passport()
            .setAge(personRequestApi.getAge())
            .setName(personRequestApi.getName())
            .setSurname(personRequestApi.getSurname());
        Person person = new Person()
            .setPassport(passport);
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, PersonRequestApi personUpdateApi) {
        Person person = findPersonSafe(id);
        Passport passport = person.getPassport();
        return personRepository.save(person.setPassport(passport
            .setName(personUpdateApi.getName())
            .setSurname(personUpdateApi.getSurname())
            .setAge(personUpdateApi.getAge()))
        );
    }

    @Transactional
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public void buyCar(Long personId, Long carId) {
        Person person = findPersonSafe(personId);
        List<Car> personCars = person.getCars();
        Car car = carRepository.findById(carId)
            .orElseThrow(() -> new CustomException(CommonErrorCode.ENTITY_NOT_FOUND));
        car.setPerson(person);
        personCars.add(car);
        carRepository.save(car);
        personRepository.save(person);
    }

    public void buyHouse(Long personId, Long houseId) {
        Person person = findPersonSafe(personId);
        List<House> personHouses = person.getHouses();
        House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new CustomException(CommonErrorCode.ENTITY_NOT_FOUND));
        personHouses.add(house);
        personRepository.save(person);
    }

    private Person findPersonSafe(Long id) {
        return personRepository.findById(id)
            .orElseThrow(() -> new CustomException(CommonErrorCode.ENTITY_NOT_FOUND));
    }
}
