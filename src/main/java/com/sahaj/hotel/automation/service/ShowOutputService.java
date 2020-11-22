package com.sahaj.hotel.automation.service;

import com.sahaj.hotel.automation.entity.FloorCorridor;
import com.sahaj.hotel.automation.entity.Hotel;
import com.sahaj.hotel.automation.entity.HotelFloor;

import java.util.List;

public class ShowOutputService {

    /**
     * Print hotel state
     * @param hotel
     */
    public void printHotelState(Hotel hotel) {
        List<HotelFloor> hotelFloorList = hotel.getFloorList();
        hotelFloorList.forEach(floor -> printFloor(floor));
    }

    public void printFloor(HotelFloor hotelFloor) {
        System.out.println("\t\tFloor : " + hotelFloor.getFloorNumber());
        List<FloorCorridor> mainCorridor = hotelFloor.getMainCorridorsList();
        List<FloorCorridor> subCorridor = hotelFloor.getSubCorridorsList();
        mainCorridor.forEach(corridor -> printMainCorridor(corridor));
        subCorridor.forEach(corridor -> printSubCorridor(corridor));
    }

    public void printMainCorridor(FloorCorridor corridor) {
        System.out.println("Main corridor " + corridor.getCorridorNumber()
                + "  Light : " + printEquipmentState(corridor.getFirstLight().isState())
         + "  AC : " + printEquipmentState(corridor.getFirstAC().isState()));
    }

    public void printSubCorridor(FloorCorridor corridor) {
        System.out.println("Sub corridor " + corridor.getCorridorNumber()
                + "  Light : " + printEquipmentState(corridor.getFirstLight().isState())
                + "  AC : " + printEquipmentState(corridor.getFirstAC().isState()));
    }

    public String printEquipmentState(boolean state) {
        return state ? "ON" : "OFF";
    }
}
