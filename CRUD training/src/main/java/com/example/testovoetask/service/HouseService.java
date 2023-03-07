package com.example.testovoetask.service;

import com.example.testovoetask.api.HouseRequestApi;
import com.example.testovoetask.entities.House;
import com.example.testovoetask.enums.CommonErrorCode;
import com.example.testovoetask.exception.CustomException;
import com.example.testovoetask.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;

   public House getSingleHouse(Long id) {
       return getHouseSafe(id);
   }

   public House createHouse(HouseRequestApi houseRequestApi) {
       return houseRepository.save(new House()
           .setCountOfFloors(houseRequestApi.getCountOfFloors())
           .setHouseType(houseRequestApi.getHouseType())
           .setStreet(houseRequestApi.getStreet()));
   }

   @Transactional
   public void deleteHouse(Long id) {
       houseRepository.deleteById(id);
   }

   public House updateHouse(Long id, HouseRequestApi houseRequestApi) {
       return houseRepository.save(getHouseSafe(id)
           .setCountOfFloors(houseRequestApi.getCountOfFloors())
           .setHouseType(houseRequestApi.getHouseType())
           .setStreet(houseRequestApi.getStreet()));
   }

   private House getHouseSafe(Long id) {
       return houseRepository.findById(id)
           .orElseThrow(() -> new CustomException(CommonErrorCode.ENTITY_NOT_FOUND));
   }
}
