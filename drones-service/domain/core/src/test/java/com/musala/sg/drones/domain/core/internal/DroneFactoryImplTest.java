package com.musala.sg.drones.domain.core.internal;

import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.DroneFactory;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.api.dto.CargoDto;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DroneFactoryImplTest {

    @Test
    void restore() {
        DroneFactory factory = new DroneFactoryImpl();
        Drone drone = factory.restore(mockDroneDto("sn"));

        assertEquals(drone.getIdentity(), new DroneIdentity("sn", DroneIdentity.Model.CRUISERWEIGHT));
        assertEquals(drone.getAvailableWeight(), drone.getAvailableWeight());
        assertEquals(drone.getMaxWeight(), drone.getMaxWeight());
        assertEquals(drone.getBatteryLevel(), drone.getBatteryLevel());
        assertEquals(State.IDLE, ((DroneImpl) drone).getFSM().getState());
        assertEquals(drone.getCargos(), drone.getCargos());
    }

    private DroneDto mockDroneDto(String sn) {
        return DroneDto.builder()
                .serialNumber(sn)
                .model(DroneIdentity.Model.CRUISERWEIGHT.name())
                .maxWeight(500)
                .availableWeight(400)
                .batteryLevel(100)
                .state(State.IDLE.name())
                .cargos(List.of(
                        new CargoDto("name", "CODE", 50, "url"),
                        new CargoDto("another_name", "CODE1", 50, "url")
                ))
                .build();
    }
}