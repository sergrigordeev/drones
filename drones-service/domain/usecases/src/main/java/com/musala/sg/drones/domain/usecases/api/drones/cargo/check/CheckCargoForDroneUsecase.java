package com.musala.sg.drones.domain.usecases.api.drones.cargo.check;

import com.musala.sg.drones.domain.core.api.Cargo;
import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.DroneFactory;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.Usecase;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import com.musala.sg.drones.domain.usecases.exception.NoDroneFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CheckCargoForDroneUsecase implements Usecase<CheckCargoQuery, CheckCargoResponse> {

    private final FindDronesPort findDronesPort;
    private final DroneFactory droneFactory;

    @Override
    public CheckCargoResponse execute(CheckCargoQuery query) {
        DroneSearchQuery searchDroneQuery = convertToDroneSearchQuery(query);
        DroneDto droneDto = findDronesPort.findBy(searchDroneQuery).orElseThrow(() -> new NoDroneFoundException(searchDroneQuery));
        Drone drone = droneFactory.restore(droneDto);
        return convert(drone);
    }

    protected DroneSearchQuery convertToDroneSearchQuery(CheckCargoQuery query) {
        return new DroneSearchQuery(query.serialNumber());
    }

    protected CheckCargoResponse convert(Drone drone) {
        List<MedicationResponse> medication = convertMedications(drone.getCargos());
        return new CheckCargoResponse(drone.getMaxWeight(), drone.getAvailableWeight(), medication);
    }

    protected List<MedicationResponse> convertMedications(List<Cargo> cargos) {
        if (cargos == null || cargos.isEmpty()) {
            return List.of();
        }
        return cargos.stream().map(c -> new MedicationResponse(c.getName(), c.getCode(), c.getWeight(), c.getImageUrl())).toList();
    }
}
