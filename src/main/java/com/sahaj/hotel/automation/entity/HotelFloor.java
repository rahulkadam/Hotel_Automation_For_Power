package com.sahaj.hotel.automation.entity;

import com.sahaj.hotel.automation.entity.hotelenum.CorridorType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class HotelFloor {

    private List<FloorCorridor> mainCorridorsList;
    private List<FloorCorridor> subCorridorsList;
    private int totalMainCorridors;
    private int totalSubCorridors;
    private int floorNumber;

    public HotelFloor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public FloorCorridor getFloorCorridorByTypeAndNumber(CorridorType type, int corridorNumber) {
        if (type.equals(CorridorType.MAIN)) {
            return mainCorridorsList.size() >= corridorNumber ? mainCorridorsList.get(corridorNumber-1) : null;
        } else {
            return subCorridorsList.size() >= corridorNumber ? subCorridorsList.get(corridorNumber-1) : null;
        }
    }
}
