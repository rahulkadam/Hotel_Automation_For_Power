package com.sahaj.hotel.automation.service;

import com.sahaj.hotel.automation.constant.CommonConstants;
import com.sahaj.hotel.automation.dto.FloorPowerConsumptionStatsDto;
import com.sahaj.hotel.automation.entity.FloorCorridor;
import com.sahaj.hotel.automation.entity.Hotel;
import com.sahaj.hotel.automation.entity.HotelFloor;

import java.util.List;
import java.util.stream.Collectors;

public class PowerStatsService {

    public FloorPowerConsumptionStatsDto getFloorPowerStats(Hotel hotel, int floorNumber) {
        HotelFloor hotelFloor = hotel.getHotelFloorByNumber(floorNumber);
        FloorPowerConsumptionStatsDto dto = new FloorPowerConsumptionStatsDto();
        int totalMainCorridor = hotelFloor.getTotalMainCorridors();
        int totalSubCorridor = hotelFloor.getTotalSubCorridors();

        dto.setTotalMainCorridor(totalMainCorridor);
        dto.setTotalSubCorridor(totalSubCorridor);

        int totalPower = 0;
        List<FloorCorridor> mainCorridorList = hotelFloor.getMainCorridorsList();
        List<FloorCorridor> subCorridorList = hotelFloor.getSubCorridorsList();
        int mainCorridorPower = calculatePower(mainCorridorList);
        int subCorridorPower = calculatePower(subCorridorList);
        totalPower = mainCorridorPower + subCorridorPower;
        dto.setTotalPower(totalPower);
        dto.setSubCorridorPower(subCorridorPower);
        dto.setMainCorridorPower(mainCorridorPower);

        int expectedPower = (totalMainCorridor * CommonConstants.totalMainCorridorPower)
                + (totalSubCorridor * CommonConstants.totalSubCorridorPower);
        dto.setExpectedPower(expectedPower);
        return dto;
    }


    public int calculatePower(List<FloorCorridor> corridors) {
        int power = 0;
        power = power + corridors.stream().map(corridor -> {
            return corridor.getAcList().stream().filter(ac -> ac.isState()).collect(Collectors.summingInt(value -> value.getType().getPower()))
                    +
                    corridor.getLightList().stream().filter(ac -> ac.isState()).collect(Collectors.summingInt(value -> value.getType().getPower()));
        }).collect(Collectors.summingInt(value -> value.intValue()));
        return power;
    }
}
