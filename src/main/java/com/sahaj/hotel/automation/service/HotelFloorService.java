package com.sahaj.hotel.automation.service;

import com.sahaj.hotel.automation.dto.SensorInputDto;
import com.sahaj.hotel.automation.entity.FloorCorridor;
import com.sahaj.hotel.automation.entity.Hotel;
import com.sahaj.hotel.automation.entity.HotelFloor;
import com.sahaj.hotel.automation.entity.hotelenum.CorridorType;

import java.util.List;
import java.util.stream.Collectors;

public class HotelFloorService {

    public Hotel switchOnLight(Hotel hotel, SensorInputDto sensorInputDto) {
        int floorNumber = sensorInputDto.getFloorNumber();
        CorridorType corridorType = sensorInputDto.getType();
        int corridorNumber = sensorInputDto.getCorridorNumber();
        HotelFloor hotelFloor = hotel.getHotelFloorByNumber(floorNumber);
        FloorCorridor floorCorridor = hotelFloor.getFloorCorridorByTypeAndNumber(corridorType, corridorNumber);
        floorCorridor.getLightList().get(0).setState(true);
        return hotel;
    }

    public Hotel switchOFFLight(Hotel hotel, SensorInputDto sensorInputDto) {
        int floorNumber = sensorInputDto.getFloorNumber();
        CorridorType corridorType = sensorInputDto.getType();
        int corridorNumber = sensorInputDto.getCorridorNumber();
        HotelFloor hotelFloor = hotel.getHotelFloorByNumber(floorNumber);
        FloorCorridor floorCorridor = hotelFloor.getFloorCorridorByTypeAndNumber(corridorType, corridorNumber);
        floorCorridor.getLightList().get(0).setState(false);
        return hotel;
    }

    /**
     * Switch Off AC from Particular Corridor
     * @param hotel
     * @param floorNumber
     * @param corridorType
     * @param corridorNumber
     * @return
     */
    public Hotel switchOffAC(Hotel hotel, int floorNumber, CorridorType corridorType, int corridorNumber) {
        HotelFloor hotelFloor = hotel.getHotelFloorByNumber(floorNumber);
        FloorCorridor floorCorridor = hotelFloor.getFloorCorridorByTypeAndNumber(corridorType, corridorNumber);
        floorCorridor.getAcList().get(0).setState(false);
        return hotel;
    }

    /**
     * Switch Off AC from other SUB Corridor
     * @param hotel
     * @param sensorInputDto
     * @param acCount
     * @return
     */
    public Hotel switchOffAC(Hotel hotel, SensorInputDto sensorInputDto, int acCount) {
        int floorNumber = sensorInputDto.getFloorNumber();
        CorridorType corridorType = sensorInputDto.getType();
        int corridorNumber = sensorInputDto.getCorridorNumber();
        HotelFloor hotelFloor = hotel.getHotelFloorByNumber(floorNumber);
        List<FloorCorridor> list;

        if (corridorType.equals(CorridorType.MAIN)) {
            list = hotelFloor.getMainCorridorsList().stream()
                    .filter(corridor -> corridor.getCorridorNumber() != corridorNumber)
                    .collect(Collectors.toList());
        } else {
            list = hotelFloor.getSubCorridorsList().stream()
                    .filter(corridor -> corridor.getCorridorNumber() != corridorNumber)
                    .collect(Collectors.toList());
        }

        for (int i = 0; i < acCount && list.size() > i; i++) {
            int corridorNum = list.get(i).getCorridorNumber();
            switchOffAC(hotel, floorNumber, corridorType, corridorNum);
        }
        return hotel;
    }

    public Hotel switchOnAC(Hotel hotel, int floorNumber, CorridorType corridorType, int corridorNumber) {
        HotelFloor hotelFloor = hotel.getHotelFloorByNumber(floorNumber);
        FloorCorridor floorCorridor = hotelFloor.getFloorCorridorByTypeAndNumber(corridorType, corridorNumber);
        floorCorridor.getAcList().get(0).setState(true);
        return hotel;
    }

    /**
     * Switch on Other AC than Current Corridor
     * @param hotel
     * @param sensorInputDto
     * @return
     */
    public Hotel switchOnOneOtherAC(Hotel hotel, SensorInputDto sensorInputDto) {
        int floorNumber = sensorInputDto.getFloorNumber();
        CorridorType corridorType = sensorInputDto.getType();
        int corridorNumber = sensorInputDto.getCorridorNumber();
        HotelFloor hotelFloor = hotel.getHotelFloorByNumber(floorNumber);
        List<FloorCorridor> list;

        if (corridorType.equals(CorridorType.MAIN)) {
            list = hotelFloor.getMainCorridorsList().stream()
                    .filter(corridor -> corridor.getCorridorNumber() != corridorNumber && !corridor.getFirstAC().isState())
                    .collect(Collectors.toList());
        } else {
            list = hotelFloor.getSubCorridorsList().stream()
                    .filter(corridor -> corridor.getCorridorNumber() != corridorNumber && !corridor.getFirstAC().isState())
                    .collect(Collectors.toList());
        }

        for (int i = 0; i < 1 && list.size() > i; i++) {
            int corridorNum = list.get(i).getCorridorNumber();
            switchOnAC(hotel, floorNumber, corridorType, corridorNum);
        }
        return hotel;
    }

}
