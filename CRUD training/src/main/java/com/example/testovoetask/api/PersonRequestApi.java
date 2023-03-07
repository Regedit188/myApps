package com.example.testovoetask.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PersonRequestApi {

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotNull
    @Positive
    private Integer age;
}
