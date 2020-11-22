package com.sahaj.hotel.automation.service;

import com.sahaj.hotel.automation.dto.FloorPowerConsumptionStatsDto;
import com.sahaj.hotel.automation.dto.SensorInputDto;
import com.sahaj.hotel.automation.entity.Hotel;
import com.sahaj.hotel.automation.entity.hotelenum.CorridorType;
import com.sahaj.hotel.automation.entity.hotelenum.EquipmentType;

public class SensorMovementService {

    private HotelFloorService hotelFloorService;
    private PowerStatsService powerStatsService;

    public SensorMovementService() {
        hotelFloorService = new HotelFloorService();
        powerStatsService = new PowerStatsService();
    }

    /**
     * Switch on Light and Switch Off AC
     * 1. switch On light of corridor first
     * 2. check if need more power then switch off sub corridor AC
     * @param hotel
     * @param sensorInputDto
     * @return
     */
    public Hotel recordSensorMovement(Hotel hotel, SensorInputDto sensorInputDto) {
        int floorNumber = sensorInputDto.getFloorNumber();
        hotel = hotelFloorService.switchOnLight(hotel, sensorInputDto);
        int acCount = totalAcToSwitchOff(hotel, floorNumber);
        if (acCount > 0) {
           hotel = hotelFloorService.switchOffAC(hotel, sensorInputDto, acCount);
        }
        return hotel;
    }

    /**
     * Swith Off light and switch on AC
     * 1. switch off light first
     * 2. check if we can enable AC after that
     * @param hotel
     * @param sensorInputDto
     * @return
     */
    public Hotel recordNoSensorMovement(Hotel hotel, SensorInputDto sensorInputDto) {
        hotel = hotelFloorService.switchOFFLight(hotel, sensorInputDto);
        hotel = hotelFloorService.switchOnOneOtherAC(hotel,sensorInputDto);
        return hotel;
    }

    /**
     * Find Total AC need to be shut down after switched on light of Corridor
     * @param hotel
     * @param floorNumber
     * @return
     */
    public int totalAcToSwitchOff(Hotel hotel, int floorNumber) {
        // get Power Stats
        FloorPowerConsumptionStatsDto dto = powerStatsService.getFloorPowerStats(hotel, floorNumber);
        int powerDifference =  dto.getTotalPower() - dto.getExpectedPower();
        if (powerDifference > 0) {
            return (powerDifference/ EquipmentType.AC.getPower()) + 1;
        } else {
            return -1;
        }
    }
}
