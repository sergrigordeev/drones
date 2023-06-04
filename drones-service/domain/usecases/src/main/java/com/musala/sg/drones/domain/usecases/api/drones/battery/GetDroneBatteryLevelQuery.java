package com.musala.sg.drones.domain.usecases.api.drones.battery;

import com.musala.sg.drones.domain.usecases.api.Query;

public record GetDroneBatteryLevelQuery(String serialNumber) implements Query {
}
