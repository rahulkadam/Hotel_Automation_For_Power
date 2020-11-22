package com.sahaj.hotel.automation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BaseEntity {
    public Long id;

    public BaseEntity(Long id) {
        this.id = id;
    }
}
