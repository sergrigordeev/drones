package com.musala.sg.drones.domain.usecases.api.drones.battery;

import java.time.OffsetDateTime;

public record BatteryLevelLogDto(String serialNumber, int batteryLevel, OffsetDateTime lastCheckDateTime) {

}
