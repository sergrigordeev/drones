package com.musala.sg.drones.adapters.jpa.drone.api;

import com.musala.sg.drones.adapters.jpa.drone.internal.JpaBatteryLevelLog;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaBatteryLevelRepository;
import com.musala.sg.drones.adapters.jpa.drone.internal.mappers.JpaBatteryLevelMapper;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DronesBatteryLevelAdapterTest {
    @InjectMocks
    DronesBatteryLevelAdapter adapter;
    @Mock
    JpaBatteryLevelRepository mockJpaBatteryLevelRepository;
    @Spy
    JpaBatteryLevelMapper spyMapper = Mappers.getMapper(JpaBatteryLevelMapper.class);

    @Test
    void that_returns_optional_empty_when_no_log_exist() {

        DroneSearchQuery query = new DroneSearchQuery("SN");
        Optional<BatteryLevelLogDto> optBatteryLevel = adapter.findLatestBySerialNumber(query);

        assertTrue(optBatteryLevel.isEmpty());
        verify(mockJpaBatteryLevelRepository).findFirstBySerialNumberOrderByCreatedAtDesc("SN");
        verify(spyMapper, never()).toDto(any());
    }

    @Test
    void that_returns_result() {
        DroneSearchQuery query = new DroneSearchQuery("SN");
        JpaBatteryLevelLog entity = new JpaBatteryLevelLog();
        doReturn(Optional.of(entity)).when(mockJpaBatteryLevelRepository).findFirstBySerialNumberOrderByCreatedAtDesc("SN");
        Optional<BatteryLevelLogDto> optBatteryLevel = adapter.findLatestBySerialNumber(query);

        assertTrue(optBatteryLevel.isPresent());
        verify(mockJpaBatteryLevelRepository).findFirstBySerialNumberOrderByCreatedAtDesc("SN");
        verify(spyMapper).toDto(entity);
    }
}