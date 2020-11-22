package com.sahaj.hotel.automation.entity;

import com.sahaj.hotel.automation.entity.hotelenum.CorridorType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class FloorCorridor {

    private CorridorType type;
    private List<PowerEquipment> lightList;
    private List<PowerEquipment> acList;
    private int totalLights;
    private int totalAcs;
    private int corridorNumber;

    public PowerEquipment getFirstLight() {
        return lightList.get(0);
    }

    public PowerEquipment getFirstAC() {
        return acList.get(0);
    }
}
