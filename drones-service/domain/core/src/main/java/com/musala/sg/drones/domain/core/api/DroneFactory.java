package com.musala.sg.drones.domain.core.api;

import com.musala.sg.drones.domain.core.api.dto.DroneDto;

public interface DroneFactory {
    Drone restore(DroneDto droneDto);

    Drone create(String serialNumber, String model, int maxWeight);
}
