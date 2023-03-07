package com.example.testovoetask.api;

import com.example.testovoetask.enums.Color;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CarResponseApi {

    private Long carId;

    private Color color;

    private String model;

    private Long ownerId;
}
