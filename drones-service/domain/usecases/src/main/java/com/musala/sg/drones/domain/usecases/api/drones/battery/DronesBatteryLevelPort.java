package com.musala.sg.drones.domain.usecases.api.drones.battery;

import lombok.NonNull;

import java.util.Optional;

public interface DronesBatteryLevelPort {

    Optional<BatteryLevelLogDto> findLatestBySerialNumber(@NonNull GetDroneBatteryLevelQuery serialNumber);
}
