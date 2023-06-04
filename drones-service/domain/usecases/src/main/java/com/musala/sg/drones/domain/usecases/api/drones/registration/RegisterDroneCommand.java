package com.musala.sg.drones.domain.usecases.api.drones.registration;

import com.musala.sg.drones.domain.usecases.api.Command;

public record RegisterDroneCommand(String serialNumber, String model, int maxWeight) implements Command {

}
