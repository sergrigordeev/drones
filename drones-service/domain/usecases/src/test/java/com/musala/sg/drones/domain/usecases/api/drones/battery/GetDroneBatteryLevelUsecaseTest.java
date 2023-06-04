package com.musala.sg.drones.domain.usecases.api.drones.battery;

import com.musala.sg.drones.domain.usecases.exception.BatteryLevelNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GetDroneBatteryLevelUsecaseTest {
    @InjectMocks
    GetDroneBatteryLevelUsecase usecase;
    @Mock
    DronesBatteryLevelPort mockDronesBatteryLevelPort;

    @Test
    void that_throws_exception_when_drone_does_not_exists() {
        GetDroneBatteryLevelQuery query = new GetDroneBatteryLevelQuery("serialNumber");
        BatteryLevelNotFoundException exception = assertThrows(BatteryLevelNotFoundException.class, () -> usecase.execute(query));
        assertEquals("No battery level log found for drone with sn serialNumber", exception.getMessage());
    }
    @Test
    void that_returns_response() {
        GetDroneBatteryLevelQuery query = new GetDroneBatteryLevelQuery("serialNumber");
        OffsetDateTime lastCheckDateTime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);
        Optional<BatteryLevelLogDto> optBatteryLvl = Optional.of(new BatteryLevelLogDto("serialNumber", 77, lastCheckDateTime));
        doReturn(optBatteryLvl).when(mockDronesBatteryLevelPort).findLatestBySerialNumber(query);

        GetDroneBatteryLevelResponse execute = usecase.execute(query);

        GetDroneBatteryLevelResponse expected = new GetDroneBatteryLevelResponse("serialNumber", 77, lastCheckDateTime);
        assertEquals(expected, execute);
    }
}