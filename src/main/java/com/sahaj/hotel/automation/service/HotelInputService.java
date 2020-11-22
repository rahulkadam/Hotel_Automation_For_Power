package com.sahaj.hotel.automation.service;

import com.sahaj.hotel.automation.entity.FloorCorridor;
import com.sahaj.hotel.automation.entity.Hotel;
import com.sahaj.hotel.automation.entity.HotelFloor;
import com.sahaj.hotel.automation.entity.PowerEquipment;
import com.sahaj.hotel.automation.entity.hotelenum.CorridorType;
import com.sahaj.hotel.automation.entity.hotelenum.EquipmentType;

import java.util.ArrayList;
import java.util.List;

public class HotelInputService {

    /**
     * Initialize hotel with Name and initialize empty Floor
     * @param floorCount
     * @return
     */
    public Hotel initializeHotel(int floorCount) {
        Hotel hotel = new Hotel();
        hotel.setTotalFloor(floorCount);
        hotel.setName("Hotel With floor " + floorCount);
        List<HotelFloor> hotelFloorList  = getEmptyFloorList(floorCount);
        hotel.setFloorList(hotelFloorList);
        return hotel;
    }

    /**
     * Get Empty Floor List
     * @param floorCount
     * @return
     */
    public List<HotelFloor> getEmptyFloorList(int floorCount) {
        List<HotelFloor> hotelFloorList  = new ArrayList<>();
        for(int i=0; i < floorCount; i++) {
            hotelFloorList.add(new HotelFloor(i+1));
        }
        return hotelFloorList;
    }

    /**
     * Initialize and add Main Corridor per Floor
     * @param mainCorridorCount
     * @param hotel
     * @return
     */
    public Hotel acceptMainCorridorPerFloor(int mainCorridorCount, Hotel hotel) {
        List<HotelFloor> floorList = hotel.getFloorList();
        floorList.forEach(floor -> {
            floor.setMainCorridorsList(initializeCorridorList(mainCorridorCount, CorridorType.MAIN));
            floor.setTotalMainCorridors(mainCorridorCount);
        });

        return hotel;
    }

    /**
     * Accept Sub Corridor
     * @param subCorridorCount
     * @param hotel
     * @return
     */
    public Hotel acceptSubCorridorPerFloor(int subCorridorCount, Hotel hotel) {
        List<HotelFloor> floorList = hotel.getFloorList();
        floorList.forEach(floor -> {
            floor.setSubCorridorsList(initializeCorridorList(subCorridorCount, CorridorType.SUB));
            floor.setTotalSubCorridors(subCorridorCount);
        });

        return hotel;
    }

    /**
     * Initialize corridor List
     */
    public List<FloorCorridor> initializeCorridorList(int mainCorridorCount, CorridorType type) {
        List<FloorCorridor> list = new ArrayList<>();
        for(int i=0; i < mainCorridorCount; i++) {
            list.add(initializeCorridor(i, type));
        }
        return list;
    }

    /**
     * Initialize Corridor
     * @param index
     * @param type
     * @return
     */
    public FloorCorridor initializeCorridor(int index, CorridorType type) {
        FloorCorridor floorCorridor = new FloorCorridor();
        floorCorridor.setType(type);
        floorCorridor.setCorridorNumber(index+1);
        floorCorridor.setAcList(getListOfEquipment(1, EquipmentType.AC, type));
        floorCorridor.setTotalAcs(1);
        floorCorridor.setLightList(getListOfEquipment(1, EquipmentType.LIGHT, type));
        floorCorridor.setTotalLights(1);
        return floorCorridor;
    }

    /**
     * Initialize List of equipment in corridor // currently Only 1 light and 1 AC
     * @param count
     * @param type
     * @param corridorType
     * @return
     */
    public List<PowerEquipment> getListOfEquipment(int count, EquipmentType type, CorridorType corridorType) {
        List<PowerEquipment> list = new ArrayList<>();
        for(int i=0; i < count; i++) {
            PowerEquipment powerEquipment = new PowerEquipment();
            if (corridorType.equals(CorridorType.MAIN) || type.equals(EquipmentType.AC)) {
                powerEquipment.setState(true);
            } else {
                powerEquipment.setState(false);
            }
            powerEquipment.setType(type);
            powerEquipment.setEquipmentNumber(i + 1);
            list.add(powerEquipment);
        }
        return list;
    }

}
