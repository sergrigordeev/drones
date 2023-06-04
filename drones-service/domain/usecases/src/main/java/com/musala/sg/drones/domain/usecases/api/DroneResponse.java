package com.musala.sg.drones.domain.usecases.api;

public record DroneResponse(String serialNumber, String state, int availableWeight,
                            int batteryLevel) implements Response {
}
