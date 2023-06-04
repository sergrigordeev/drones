package com.musala.sg.drones.domain.usecases.api;

public record DroneSearchQuery(String serialNumber) implements Request {
}
