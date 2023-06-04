package com.musala.sg.drones.domain.usecases.api.drones.registration;

import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.DroneFactory;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.core.internal.DroneIdentity;
import com.musala.sg.drones.domain.usecases.api.DroneResponse;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.Usecase;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import com.musala.sg.drones.domain.usecases.api.ports.SaveDronePort;
import com.musala.sg.drones.domain.usecases.exception.DroneRegistrationException;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class RegisterDroneUsecase implements Usecase<RegisterDroneCommand, DroneResponse> {

    private final FindDronesPort findDronesPort;
    private final SaveDronePort saveDronesPort;
    private final DroneFactory droneFactory;

    @Override
    public DroneResponse execute(RegisterDroneCommand command) {
        DroneSearchQuery query = new DroneSearchQuery(command.serialNumber());
        Optional<DroneDto> optDrone = findDronesPort.findBy(query);
        if (optDrone.isPresent()) {
            throw new DroneRegistrationException("Drone with same identity exists");
        }
        try {
            Drone drone = droneFactory.create(command.serialNumber(), command.model(), command.maxWeight());
            DroneDto dto = convert(drone);
            saveDronesPort.save(dto);
            return convertToResponse(dto);
        } catch (Exception e) {
            throw new DroneRegistrationException(e.getMessage());
        }


    }

    protected DroneDto convert(Drone drone) {
        DroneIdentity identity = drone.getIdentity();
        return DroneDto.builder()
                .serialNumber(identity.getSerialNumber())
                .model(identity.getModel().name())
                .state(drone.getState())
                .maxWeight(drone.getMaxWeight())
                .availableWeight(drone.getAvailableWeight())
                .batteryLevel(drone.getBatteryLevel())
                .build();
    }

    protected DroneResponse convertToResponse(DroneDto dto) {
        return new DroneResponse(dto.getSerialNumber(), dto.getState(), dto.getAvailableWeight(), dto.getBatteryLevel());
    }
}
