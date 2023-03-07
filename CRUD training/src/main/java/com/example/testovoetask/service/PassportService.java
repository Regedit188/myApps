package com.example.testovoetask.service;

import com.example.testovoetask.entities.Passport;
import com.example.testovoetask.repository.PassportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassportService {

    private final PassportRepository passportRepository;

    public List<Passport> getAllPassports() {
        return passportRepository.findAll();
    }

    public List<Passport> getAllPassportsBySurname(char l) {
        return passportRepository.findAllPassportsBySurnameLetter(l);
    }
}
