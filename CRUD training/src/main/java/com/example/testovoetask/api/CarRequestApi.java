package com.example.testovoetask.api;

import com.example.testovoetask.enums.Color;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarRequestApi {

    @NotEmpty
    private String model;

    @NotNull
    private Color color;
}
