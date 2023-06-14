package com.musala.sg.drones.domain.usecases.api.drones.availability;

import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.DroneFactory;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.DroneResponse;
import com.musala.sg.drones.domain.usecases.api.Usecase;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class GetAvailableDronesUsecase implements Usecase<GetAvailableDronesQuery, GetAvailableDronesResponse> {
    private final FindDronesPort findDronesPort;
    private final DroneFactory droneFactory;

    @Override
    public GetAvailableDronesResponse execute(@NonNull GetAvailableDronesQuery query) {
        List<DroneDto> allAvailableDrones = findDronesPort.findAllAvailableDrones(query);
        return convert(allAvailableDrones);
    }

    protected GetAvailableDronesResponse convert(List<DroneDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new GetAvailableDronesResponse(List.of());
        }
        List<DroneResponse> converted = new ArrayList<>(dtos.size());
        for (DroneDto dto : dtos) {
            try {
                Drone drone = droneFactory.restore(dto);
                converted.add(convert(drone));
            } catch (Exception e) {
                log.error("can not transform dto to response", e);
            }

        }
        return new GetAvailableDronesResponse(converted.stream().toList());
    }

    private DroneResponse convert(Drone drone) {
        return new DroneResponse(drone.getIdentity().getSerialNumber(), drone.getState(), drone.getAvailableWeight(), drone.getBatteryLevel());
    }
}
