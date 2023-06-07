package com.musala.sg.drones.domain.usecases.api.drones.cargo.load;

import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.DroneFactory;
import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.api.dto.CargoDto;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.core.internal.DroneFactoryImpl;
import com.musala.sg.drones.domain.core.internal.DroneIdentity;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import com.musala.sg.drones.domain.usecases.api.ports.SaveDronePort;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoadMedicationUsecaseTest {
    @InjectMocks
    LoadMedicationUsecase usecase;
    @Mock
    FindDronesPort mockFindDronesPort;
    @Mock
    SaveDronePort mockSaveDronePort;
    @Spy
    DroneFactory spyDroneFactory = new DroneFactoryImpl();

    @Test
    void that_throws_when_drone_not_found_exception_when_no_drone_exists_by_sn() {
        LoadMedicationCommand command = new LoadMedicationCommand("sn", new CargoDto("name", "CODE", 1, ""));

        NoDroneFoundException exception = assertThrows(NoDroneFoundException.class, () -> usecase.execute(command));

        assertEquals("No drone with SN sn has been found", exception.getMessage());
    }

    @Test
    void that_throws_when_medication_data_is_incorrect() {
        LoadMedicationCommand command = new LoadMedicationCommand("sn", new CargoDto("na me", "CODE", 1, ""));
        DroneSearchQuery droneSearchQuery = new DroneSearchQuery("sn");
        doReturn(Optional.of(mockDroneDto("sn"))).when(mockFindDronesPort).findBy(droneSearchQuery);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> usecase.execute(command));

        assertEquals("Name should contains only letters, '_', '-', but it is na me", exception.getMessage());
    }

    @Test
    void that_calls_all_required_method() {
        LoadMedicationCommand command = new LoadMedicationCommand("dto", new CargoDto("name", "CODE", 1, ""));
        DroneSearchQuery droneSearchQuery = new DroneSearchQuery("dto");
        DroneDto dto = mockDroneDto("dto");
        doReturn(Optional.of(dto)).when(mockFindDronesPort).findBy(droneSearchQuery);
        Drone mockDrone = mock(Drone.class);
        doReturn(mockDrone).when(spyDroneFactory).restore(dto);


        LoadMedicationUsecase spyUsecase = spy(usecase);
        doReturn(mock(Medication.class)).when(spyUsecase).convertMedication(any(CargoDto.class));

        LoadMedicationResponse execute = spyUsecase.execute(command);

        verify(mockFindDronesPort).findBy(droneSearchQuery);
        verify(spyUsecase).convertToDroneSearchQuery(command);
        verify(spyDroneFactory).restore(dto);
        verify(spyDroneFactory).restore(dto);
        verify(mockDrone).load(any(Medication.class));
        verify(mockSaveDronePort).loadMedication(any());
    }

    private DroneDto mockDroneDto(String sn) {
        return DroneDto.builder()
                .serialNumber(sn)
                .model(DroneIdentity.Model.CRUISERWEIGHT.name())
                .maxWeight(500)
                .availableWeight(400)
                .batteryLevel(100)
                .state(State.LOADING.name())
                .cargos(List.of(
                        new CargoDto("name", "CODE", 50, "url"),
                        new CargoDto("another_name", "CODE1", 50, "url")
                ))
                .build();
    }
}