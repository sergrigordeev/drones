package com.musala.sg.drones.adapters.jpa.drone.api;

import com.musala.sg.drones.adapters.jpa.drone.internal.JpaBatteryLevelLog;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaBatteryLevelRepository;
import com.musala.sg.drones.adapters.jpa.drone.internal.mappers.JpaBatteryLevelMapper;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import com.musala.sg.drones.domain.usecases.api.ports.DronesBatteryLevelPort;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class DronesBatteryLevelAdapter implements DronesBatteryLevelPort {

    private final JpaBatteryLevelRepository batteryLevelRepository;
    private final JpaBatteryLevelMapper jpaBatteryLevelMapper;
    @Override
    public Optional<BatteryLevelLogDto> findLatestBySerialNumber(@NonNull DroneSearchQuery query) {
        Optional<JpaBatteryLevelLog> optJpaBatteryLevelLog = batteryLevelRepository.findFirstBySerialNumberOrderByCreatedAtDesc(query.serialNumber());

        return optJpaBatteryLevelLog.map(jpaBatteryLevelMapper::toDto);
    }
}
