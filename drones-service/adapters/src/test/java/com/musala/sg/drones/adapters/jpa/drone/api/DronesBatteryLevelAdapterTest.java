package com.musala.sg.drones.adapters.jpa.drone.api;

import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDroneStateLog;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDroneStateRepository;
import com.musala.sg.drones.adapters.jpa.drone.internal.mappers.JpaDroneStateLogMapper;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import com.musala.sg.drones.domain.usecases.api.ports.dto.DroneStatusDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DronesBatteryLevelAdapterTest {
    @InjectMocks
    DronesBatteryLevelAdapter adapter;
    @Mock
    JpaDroneStateRepository mockJpaDroneStateRepository;
    @Spy
    JpaDroneStateLogMapper spyMapper = Mappers.getMapper(JpaDroneStateLogMapper.class);

    @Test
    void that_returns_optional_empty_when_no_log_exist() {

        DroneSearchQuery query = new DroneSearchQuery("SN");
        Optional<BatteryLevelLogDto> optBatteryLevel = adapter.findLatestBySerialNumber(query);

        assertTrue(optBatteryLevel.isEmpty());
        verify(mockJpaDroneStateRepository).findFirstBySerialNumberOrderByCreatedAtDesc("SN");
        verify(spyMapper, never()).toDto(any());
    }

    @Test
    void that_returns_result() {
        DroneSearchQuery query = new DroneSearchQuery("SN");
        JpaDroneStateLog entity = new JpaDroneStateLog();
        entity.setCreatedAt(LocalDateTime.MIN);
        doReturn(Optional.of(entity)).when(mockJpaDroneStateRepository).findFirstBySerialNumberOrderByCreatedAtDesc("SN");
        Optional<BatteryLevelLogDto> optBatteryLevel = adapter.findLatestBySerialNumber(query);

        assertTrue(optBatteryLevel.isPresent());
        verify(mockJpaDroneStateRepository).findFirstBySerialNumberOrderByCreatedAtDesc("SN");
        verify(spyMapper).toDto(entity);
    }

    @Test
    void that_save_entity() {
        DroneStatusDto dto = DroneStatusDto.builder().serialNumber("SN").build();

        adapter.addLogEntry(dto);

        verify(mockJpaDroneStateRepository).save(argThat(e -> e.getSerialNumber().equals("SN")));
        verify(spyMapper).partialUpdate(eq(dto), argThat(e -> e.getSerialNumber().equals("SN")));
    }
}