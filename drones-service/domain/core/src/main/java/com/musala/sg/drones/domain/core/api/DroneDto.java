package com.musala.sg.drones.domain.core.api;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class DroneDto {

    private String serialNumber;
    private State state;
    private int maxWeight;
    private int availableWeight;
    private int batteryLevel;
}
