package com.musala.sg.drones.domain.usecases.api.drones.cargo.load;

import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.DroneFactory;
import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.dto.CargoDto;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.Usecase;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import com.musala.sg.drones.domain.usecases.api.ports.SaveDronePort;
import com.musala.sg.drones.domain.usecases.exception.NoDroneFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class LoadMedicationUsecase implements Usecase<LoadMedicationCommand, LoadMedicationResponse> {
    private final FindDronesPort findDronesPort;
    private final SaveDronePort saveDronesPort;
    private final DroneFactory droneFactory;

    @Override
    public LoadMedicationResponse execute(@NonNull LoadMedicationCommand command) {
        DroneSearchQuery query = convertToDroneSearchQuery(command);
        DroneDto droneDto = findDronesPort.findBy(query).orElseThrow(() -> new NoDroneFoundException(query));
        Drone drone = droneFactory.restore(droneDto);
        Medication medication = convertMedication(command.medication());
        drone.load(medication);
        saveDronesPort.loadMedication(drone.getIdentity(), medication);
        return new LoadMedicationResponse();
    }

    protected Medication convertMedication(CargoDto cargo) {
        return new Medication(cargo.getName(), cargo.getCode(),cargo.getWeight(), cargo.getImageUrl());
    }

    protected DroneSearchQuery convertToDroneSearchQuery(LoadMedicationCommand command) {
        return new DroneSearchQuery(command.serialNumber());
    }

}
