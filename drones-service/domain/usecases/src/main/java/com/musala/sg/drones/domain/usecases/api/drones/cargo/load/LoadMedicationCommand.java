package com.musala.sg.drones.domain.usecases.api.drones.cargo.load;

import com.musala.sg.drones.domain.core.api.dto.CargoDto;
import com.musala.sg.drones.domain.usecases.api.Command;

public record LoadMedicationCommand(String serialNumber, CargoDto medication) implements Command {
}
