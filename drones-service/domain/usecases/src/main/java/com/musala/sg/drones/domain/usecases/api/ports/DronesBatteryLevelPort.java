package com.musala.sg.drones.domain.usecases.api.ports;

import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import lombok.NonNull;

import java.util.Optional;

public interface DronesBatteryLevelPort {

    Optional<BatteryLevelLogDto> findLatestBySerialNumber(@NonNull DroneSearchQuery serialNumber);
}
