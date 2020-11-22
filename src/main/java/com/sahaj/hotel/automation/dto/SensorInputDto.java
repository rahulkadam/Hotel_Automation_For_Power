package com.sahaj.hotel.automation.dto;

import com.sahaj.hotel.automation.entity.hotelenum.CorridorType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class SensorInputDto {

    private int corridorNumber;
    private CorridorType type;
    private int floorNumber;
    private boolean isMovement;
}
