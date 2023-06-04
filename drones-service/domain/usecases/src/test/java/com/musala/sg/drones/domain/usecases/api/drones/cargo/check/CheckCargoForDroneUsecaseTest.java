package com.musala.sg.drones.domain.usecases.api.drones.cargo.check;

import com.musala.sg.drones.domain.core.api.dto.CargoDto;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.core.api.DroneFactory;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneFactoryImpl;
import com.musala.sg.drones.domain.core.internal.DroneIdentity;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import com.musala.sg.drones.domain.usecases.exception.NoDroneFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CheckCargoForDroneUsecaseTest {
    @InjectMocks
    CheckCargoForDroneUsecase usecase;
    @Mock
    FindDronesPort mockFindDronesPort;
    @Spy
    DroneFactory spyDroneFactory = new DroneFactoryImpl();

    @Test
    void that_throws_drone_not_found_exception_when_no_drone_exists_by_sn() {
        CheckCargoQuery sn = new CheckCargoQuery("sn");
        NoDroneFoundException exception = assertThrows(NoDroneFoundException.class, () -> usecase.execute(sn));
        assertEquals("No drone with SN sn has been found", exception.getMessage());
    }

    @Test
    void that_returns_response() {
        CheckCargoQuery query = new CheckCargoQuery("sn");
        doReturn(Optional.of(mockDroneDto("sn"))).when(mockFindDronesPort).findBySerialNumber(query);

        CheckCargoResponse response = usecase.execute(query);

        CheckCargoResponse expected = new CheckCargoResponse(500, 400, List.of(
                mockMedication("name", "CODE", 50),
                mockMedication("another_name", "CODE1", 50))
        );
        assertEquals(expected, response);
    }

    private MedicationResponse mockMedication(String name, String code, int weight) {
        String imgUrl = "url";
        return new MedicationResponse(name, code, weight, imgUrl);
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