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

        int percents = (int) (100 * (minute / 60));
        return DroneStatusDto.builder()
                .serialNumber(request.serialNumber())
                .batteryLevel(percents)
                .success(true)
                .build();
    }
}
