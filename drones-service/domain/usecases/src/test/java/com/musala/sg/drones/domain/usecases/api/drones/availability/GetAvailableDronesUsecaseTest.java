package com.musala.sg.drones.domain.usecases.api.drones.availability;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.DroneResponse;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GetAvailableDronesUsecaseTest {

    @InjectMocks
    GetAvailableDronesUsecase usecase;
    @Mock
    FindDronesPort mockFindDronesPort;

    @Test
    void that_returns_empty_list_when_no_drones_available() {
        GetAvailableDronesQuery request = new GetAvailableDronesQuery();

        GetAvailableDronesResponse response = usecase.execute(request);

        assertEquals(0, response.drones().size());
    }

    @Test
    void that_returns_list() {
        GetAvailableDronesQuery query = new GetAvailableDronesQuery();
        doReturn(List.of(
                mockDroneDto("sn1", 100, 75),
                mockDroneDto("sn2", 30, 25),
                mockDroneDto("sn3", 200, 45))
        ).when(mockFindDronesPort).findAllAvailableDrones(query);
        GetAvailableDronesResponse response = usecase.execute(query);

        assertEquals(3, response.drones().size());
        assertEquals(new DroneResponse("sn1", State.IDLE.name(), 100, 75), response.drones().get(0));
        assertEquals(new DroneResponse("sn2", State.IDLE.name(), 30, 25), response.drones().get(1));
        assertEquals(new DroneResponse("sn3", State.IDLE.name(), 200, 45), response.drones().get(2));

    }

    private DroneDto mockDroneDto(String sn, int availableWeight, int batteryLevel) {
        return DroneDto.builder()
                .serialNumber(sn)
                .maxWeight(500)
                .availableWeight(availableWeight)
                .batteryLevel(batteryLevel)
                .state(State.IDLE.name())
                .build();
    }
}