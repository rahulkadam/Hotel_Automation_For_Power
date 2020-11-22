package com.sahaj.hotel.automation.entity.hotelenum;

public enum EquipmentType {
    LIGHT(5), AC(10);

    private int power;

    EquipmentType(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

}
