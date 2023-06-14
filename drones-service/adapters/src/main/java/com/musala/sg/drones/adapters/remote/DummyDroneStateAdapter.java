package com.musala.sg.drones.adapters.remote;

import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.ports.RuntimeDroneStatusCheckPort;
import com.musala.sg.drones.domain.usecases.api.ports.dto.DroneStatusDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DummyDroneStateAdapter implements RuntimeDroneStatusCheckPort {

    @Override
    public DroneStatusDto check(DroneSearchQuery request) {
        float minute = LocalDateTime.now().getMinute();

        float percents = Math.min(100, (100 * (minute / 60)) + 25);
        return DroneStatusDto.builder()
                .serialNumber(request.serialNumber())
                .batteryLevel((int) percents)
                .success(true)
                .build();
    }
}
