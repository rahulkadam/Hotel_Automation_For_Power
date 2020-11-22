package com.sahaj.hotel.automation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class FloorPowerConsumptionStatsDto {

    int totalPower;
    int mainCorridorPower;
    int subCorridorPower;
    int totalMainCorridor;
    int totalSubCorridor;
    int expectedPower;
}
