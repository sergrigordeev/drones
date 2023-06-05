package com.musala.sg.drones.domain.usecases.api.ports;

import com.musala.sg.drones.domain.usecases.api.ports.dto.DroneStatusDto;

public interface RuntimeDroneStatusReportPort {
    void save(DroneStatusDto dto) ;
}
