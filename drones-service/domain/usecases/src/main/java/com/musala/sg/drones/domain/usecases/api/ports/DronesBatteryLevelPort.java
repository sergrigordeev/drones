package com.musala.sg.drones.domain.usecases.api.ports;

import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import com.musala.sg.drones.domain.usecases.api.drones.battery.GetDroneBatteryLevelQuery;
import lombok.NonNull;

import java.util.Optional;

public interface DronesBatteryLevelPort {

    Optional<BatteryLevelLogDto> findLatestBySerialNumber(@NonNull GetDroneBatteryLevelQuery serialNumber);
}
