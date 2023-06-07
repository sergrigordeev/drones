package com.musala.sg.drones.domain.usecases.api.ports;

import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.load.LoadMedicationCommand;

public interface SaveDronePort {
    void save(DroneDto any);

    void loadMedication(LoadMedicationCommand command);
}
