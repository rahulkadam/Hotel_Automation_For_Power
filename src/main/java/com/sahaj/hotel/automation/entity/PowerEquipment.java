package com.sahaj.hotel.automation.entity;

import com.sahaj.hotel.automation.entity.hotelenum.EquipmentType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class PowerEquipment {

    private EquipmentType type;
    private boolean state;
    private int equipmentNumber;
}
