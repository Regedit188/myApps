package com.example.testovoetask.api;

import com.example.testovoetask.enums.HouseType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HouseRequestApi {

    @NotNull
    private Integer countOfFloors;

    @NotNull
    private HouseType houseType;

    @NotEmpty
    private String street;
}
