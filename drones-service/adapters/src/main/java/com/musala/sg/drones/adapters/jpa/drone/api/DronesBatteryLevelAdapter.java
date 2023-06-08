package com.musala.sg.drones.adapters.jpa.drone.api;

import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDroneStateLog;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDroneStateRepository;
import com.musala.sg.drones.adapters.jpa.drone.internal.mappers.JpaDroneStateLogMapper;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import com.musala.sg.drones.domain.usecases.api.ports.DronesBatteryLevelPort;
import com.musala.sg.drones.domain.usecases.api.ports.RuntimeDroneStatusReportPort;
import com.musala.sg.drones.domain.usecases.api.ports.dto.DroneStatusDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class DronesBatteryLevelAdapter implements DronesBatteryLevelPort, RuntimeDroneStatusReportPort {

    private final JpaDroneStateRepository jpaBatteryLevelRepository;
    private final JpaDroneStateLogMapper jpaDroneStateLogMapper;
    @Override
    public Optional<BatteryLevelLogDto> findLatestBySerialNumber(@NonNull DroneSearchQuery query) {
        Optional<JpaDroneStateLog> optJpaBatteryLevelLog = jpaBatteryLevelRepository.findFirstBySerialNumberOrderByCreatedAtDesc(query.serialNumber());

        return optJpaBatteryLevelLog.map(jpaDroneStateLogMapper::toDto);
    }

    public void addLogEntry(@NonNull DroneStatusDto droneStatusDto){

        JpaDroneStateLog newLogEntry = new JpaDroneStateLog();
        JpaDroneStateLog jpaDroneStateLog = jpaDroneStateLogMapper.partialUpdate(droneStatusDto, newLogEntry);
        jpaBatteryLevelRepository.save(jpaDroneStateLog);

    }
}
