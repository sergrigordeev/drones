package com.musala.sg.drones.domain.usecases.api.ports;

import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.core.internal.DroneIdentity;

public interface SaveDronePort {
    void save(DroneDto any);

    void loadMedication(DroneIdentity identity, Medication medication);
}
