package com.musala.sg.drones.domain.usecases.api.drones.realtime.check;

import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.Usecase;
import com.musala.sg.drones.domain.usecases.api.ports.RuntimeDroneStatusCheckPort;
import com.musala.sg.drones.domain.usecases.api.ports.RuntimeDroneStatusReportPort;
import com.musala.sg.drones.domain.usecases.api.ports.dto.DroneStatusDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import static com.musala.sg.drones.domain.usecases.api.drones.realtime.check.DroneStatusResponse.Result.AVAILABLE;
import static com.musala.sg.drones.domain.usecases.api.drones.realtime.check.DroneStatusResponse.Result.UNAVAILABLE;

@Component
@AllArgsConstructor
public class DroneRuntimeStatusCheckUsecase implements Usecase<DroneSearchQuery, DroneStatusResponse> {
    private final RuntimeDroneStatusCheckPort droneStatusCheckPort;
    private final RuntimeDroneStatusReportPort runtimeDroneStatusReportPort;
    private final TransactionTemplate trxTemplate;

    @Override
    public DroneStatusResponse execute(DroneSearchQuery request) {
        DroneStatusDto dto = droneStatusCheckPort.check(request);
        updateStatus(dto);
        return convert(dto);
    }

    protected void updateStatus(DroneStatusDto dto) {
        trxTemplate.executeWithoutResult(status -> runtimeDroneStatusReportPort.save(dto));
    }

    protected DroneStatusResponse convert(DroneStatusDto dto) {
        DroneStatusResponse.Result result = dto.isSuccess() ? AVAILABLE : UNAVAILABLE;
        return new DroneStatusResponse(result);
    }
}
