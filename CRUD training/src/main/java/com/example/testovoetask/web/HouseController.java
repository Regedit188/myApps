package com.example.testovoetask.web;

import com.example.testovoetask.api.HouseRequestApi;
import com.example.testovoetask.api.HouseResponseApi;
import com.example.testovoetask.mappers.HouseMapper;
import com.example.testovoetask.service.HouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Validated
public class HouseController {

    private final HouseService houseService;

    private final HouseMapper houseMapper;

    @GetMapping(path = "/api/houses/{id}")
    public HouseResponseApi getSingleHouse(@PathVariable Long id) {
        return houseMapper.toResponseApi(houseService.getSingleHouse(id));
    }

    @PostMapping(path = "/api/houses/create")
    public HouseResponseApi createHouse(@RequestBody @Valid HouseRequestApi houseRequestApi) {
        return houseMapper.toResponseApi(houseService.createHouse(houseRequestApi));
    }

    @DeleteMapping(path = "/api/houses/{id}/delete")
    public void deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
    }

    @PutMapping(path = "/api/houses/{id}/update")
    public HouseResponseApi updateHouse(@PathVariable Long id, @RequestBody HouseRequestApi houseRequestApi) {
        return houseMapper.toResponseApi(houseService.updateHouse(id, houseRequestApi));
    }
}
