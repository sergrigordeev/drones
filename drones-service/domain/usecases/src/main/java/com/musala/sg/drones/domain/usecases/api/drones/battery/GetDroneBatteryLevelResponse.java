package com.musala.sg.drones.domain.usecases.api.drones.battery;

import com.musala.sg.drones.domain.usecases.api.Response;

import java.time.OffsetDateTime;

public record GetDroneBatteryLevelResponse(String serialNumber, int batteryLevel,
                                           OffsetDateTime dateTime) implements Response {
}
