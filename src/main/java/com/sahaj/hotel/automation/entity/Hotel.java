package com.sahaj.hotel.automation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Data
@ToString
public class Hotel extends BaseEntity {
    private String name;
    private List<HotelFloor> floorList;
    private int totalFloor;

    public HotelFloor getHotelFloorByNumber(int number) {
        return floorList.size() >= number ? floorList.get(number-1) : null;
    }
}
