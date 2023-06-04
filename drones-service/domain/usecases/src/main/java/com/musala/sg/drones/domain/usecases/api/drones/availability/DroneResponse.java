package com.musala.sg.drones.domain.usecases.api.drones.availability;

public record DroneResponse(String sn, int availableWeight, int batteryLevel) {
}
