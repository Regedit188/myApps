package com.example.testovoetask.api;

import com.example.testovoetask.enums.HouseType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class HouseResponseApi {

    private Long houseId;

    private int countOfFloors;

    private HouseType houseType;

    private String street;

    private List<Long> ownersIds;
}
