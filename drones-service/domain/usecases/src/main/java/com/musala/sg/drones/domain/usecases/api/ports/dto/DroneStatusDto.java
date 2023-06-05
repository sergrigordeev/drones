package com.musala.sg.drones.domain.usecases.api.ports.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DroneStatusDto {

    private final String serialNumber;
    private final int batteryLevel;
    private boolean success;
    //TODO report date, coordinates etc
}
