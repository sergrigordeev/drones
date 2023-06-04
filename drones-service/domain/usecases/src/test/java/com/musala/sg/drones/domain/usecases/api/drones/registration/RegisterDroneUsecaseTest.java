package com.musala.sg.drones.domain.usecases.api.drones.registration;

import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.DroneFactory;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.core.internal.DroneFactoryImpl;
import com.musala.sg.drones.domain.usecases.api.DroneResponse;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import com.musala.sg.drones.domain.usecases.api.ports.SaveDronePort;
import com.musala.sg.drones.domain.usecases.exception.DroneRegistrationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterDroneUsecaseTest {
    @InjectMocks
    RegisterDroneUsecase usecase;
    @Mock
    FindDronesPort mockFindDronesPort;
    @Mock
    SaveDronePort mockSaveDronePort;
    @Spy
    DroneFactory spyDroneFactory = new DroneFactoryImpl();

    @Test
    void that_throws_when_drone_exists() {
        RegisterDroneCommand command = new RegisterDroneCommand("sn", "LIGHTWEIGHT", 300);

        DroneSearchQuery query = new DroneSearchQuery("sn");
        DroneDto mockDrone = mock();
        doReturn(Optional.of(mockDrone)).when(mockFindDronesPort).findBy(query);
        DroneRegistrationException exception = assertThrows(DroneRegistrationException.class, () -> usecase.execute(command));

        assertEquals("Drone with same identity exists", exception.getMessage());
    }

    @Test
    void that_throws_when_command_is_invalid() {
        RegisterDroneCommand command = new RegisterDroneCommand("s n", "LIGHTWEIGHT", 600);

        DroneRegistrationException exception = assertThrows(DroneRegistrationException.class, () -> usecase.execute(command));

        assertEquals("Max weight can not be less than 0, but it is 600", exception.getMessage());
    }

    @Test
    void that_calls_all_required_method() {
        RegisterDroneCommand command = new RegisterDroneCommand("s n", "LIGHTWEIGHT", 500);

        RegisterDroneUsecase spyUsecase = spy(usecase);
        DroneDto mockDto = mock();
        doReturn(mockDto).when(spyUsecase).convert(any(Drone.class));
        DroneResponse response = spyUsecase.execute(command);

        assertNotNull(response);
        verify(spyUsecase).convert(any(Drone.class));
        verify(spyUsecase).convertToResponse(mockDto);
        verify(spyDroneFactory).create("s n", "LIGHTWEIGHT", 500);
        verify(mockFindDronesPort).findBy(new DroneSearchQuery("s n"));
        verify(mockSaveDronePort).save(any(DroneDto.class));

    }

    @Test
    void that_convert_returns_dto() {
        Drone drone = spyDroneFactory.create("s n", "LIGHTWEIGHT", 500);
        DroneDto dto = usecase.convert(drone);
        assertEquals("s n", dto.getSerialNumber());
        assertEquals("LIGHTWEIGHT", dto.getModel());
        assertEquals("IDLE", dto.getState());
        assertEquals(500, dto.getMaxWeight());
        assertEquals(500, dto.getAvailableWeight());
        assertEquals(100, dto.getBatteryLevel());
        assertNull(dto.getCargos());
    }

    @Test
    void that_convertToResponse_returns_response() {
        Drone drone = spyDroneFactory.create("s n", "LIGHTWEIGHT", 500);
        DroneDto dto = usecase.convert(drone);
        DroneResponse response = usecase.convertToResponse(dto);
        assertEquals("s n", response.serialNumber());
        assertEquals("IDLE", response.state());
        assertEquals(500, response.availableWeight());
        assertEquals(100, response.batteryLevel());
    }
}