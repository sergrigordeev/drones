package com.musala.sg.drones.domain.usecases.api.ports;

import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.ports.dto.DroneStatusDto;

public interface RuntimeDroneStatusCheckPort {
    DroneStatusDto check(DroneSearchQuery request);
}
