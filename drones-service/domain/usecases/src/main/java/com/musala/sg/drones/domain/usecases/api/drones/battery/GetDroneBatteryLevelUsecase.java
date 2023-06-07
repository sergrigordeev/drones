package com.musala.sg.drones.domain.usecases.api.drones.battery;

import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.Usecase;
import com.musala.sg.drones.domain.usecases.api.ports.DronesBatteryLevelPort;
import com.musala.sg.drones.domain.usecases.exception.BatteryLevelNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetDroneBatteryLevelUsecase implements Usecase<DroneSearchQuery, GetDroneBatteryLevelResponse> {
    private final DronesBatteryLevelPort findDronesPort;

    @Override
    public GetDroneBatteryLevelResponse execute(@NonNull DroneSearchQuery request) {
        BatteryLevelLogDto batteryLevelLogDto = findDronesPort.findLatestBySerialNumber(request).orElseThrow(() -> new BatteryLevelNotFoundException(request));
        return convert(batteryLevelLogDto);
    }

    GetDroneBatteryLevelResponse convert(BatteryLevelLogDto dto) {
        return new GetDroneBatteryLevelResponse(dto.serialNumber(), dto.batteryLevel(), dto.lastCheckDateTime());
    }
}
