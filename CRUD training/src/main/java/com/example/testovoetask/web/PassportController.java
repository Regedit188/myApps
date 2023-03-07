package com.example.testovoetask.web;

import com.example.testovoetask.entities.Passport;
import com.example.testovoetask.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class PassportController {

    private final PassportService passportService;

    @GetMapping(path = "/api/passport")
    public List<Passport> getAllPassports() {
        return passportService.getAllPassports();
    }


    @GetMapping(path = "/api/passport/{letter}")
    public List<Passport> getAllPassportsBySurname(@PathVariable char letter) {
        return passportService.getAllPassportsBySurname(letter);
    }
}
