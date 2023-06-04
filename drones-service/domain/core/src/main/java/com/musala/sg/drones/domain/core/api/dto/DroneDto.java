package com.musala.sg.drones.domain.core.api.dto;

import com.musala.sg.drones.domain.core.api.Cargo;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DroneDto {

    private String serialNumber;
    private String model;
    private int maxWeight;
    private int availableWeight;
    private int batteryLevel;
    private String state;
    List<Cargo> cargos;

}
