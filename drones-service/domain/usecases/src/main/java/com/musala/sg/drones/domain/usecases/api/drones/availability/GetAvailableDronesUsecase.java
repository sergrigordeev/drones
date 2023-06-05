package com.musala.sg.drones.domain.usecases.api.drones.availability;

import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.DroneResponse;
import com.musala.sg.drones.domain.usecases.api.Usecase;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class GetAvailableDronesUsecase implements Usecase<GetAvailableDronesQuery, GetAvailableDronesResponse> {
    private final FindDronesPort findDronesPort;

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
            converted.add(convert(dto));
        }
        return new GetAvailableDronesResponse(converted.stream().toList());
    }

    private DroneResponse convert(DroneDto dto) {
        return new DroneResponse(dto.getSerialNumber(), dto.getState(), dto.getAvailableWeight(), dto.getBatteryLevel());
    }
}
