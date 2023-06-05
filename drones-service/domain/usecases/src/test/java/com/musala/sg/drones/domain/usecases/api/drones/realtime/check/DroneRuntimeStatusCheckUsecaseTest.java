package com.musala.sg.drones.domain.usecases.api.drones.realtime.check;

import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.ports.RuntimeDroneStatusReportPort;
import com.musala.sg.drones.domain.usecases.api.ports.RuntimeDroneStatusCheckPort;
import com.musala.sg.drones.domain.usecases.api.ports.dto.DroneStatusDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DroneRuntimeStatusCheckUsecaseTest {
    @InjectMocks
    DroneRuntimeStatusCheckUsecase usecase;
    @Mock
    RuntimeDroneStatusCheckPort mockRuntimeStatusCheckPort;
    @Mock
    RuntimeDroneStatusReportPort mockRuntimeDroneStatusReportPort;
    @Mock
    TransactionTemplate mockTransactionTemplate;

    @Test
    void that_calls_all_ports_and_internal_methods() {
        DroneSearchQuery query = new DroneSearchQuery("sn");
        DroneStatusDto dto = DroneStatusDto.builder().build();
        doReturn(dto).when(mockRuntimeStatusCheckPort).check(query);
        DroneRuntimeStatusCheckUsecase spyUsecase = spy(usecase);
        DroneStatusResponse response = spyUsecase.execute(query);

        assertNotNull(response);
        verify(mockRuntimeStatusCheckPort).check(query);
        verify(spyUsecase).updateStatus(dto);
        verify(spyUsecase).convert(dto);

    }

    @Test
    void that_convert_returns_unavailable_result() {
        DroneStatusDto dto = DroneStatusDto.builder().build();
        DroneStatusResponse converted = usecase.convert(dto);
        assertEquals(DroneStatusResponse.Result.UNAVAILABLE, converted.result());
    }

    @Test
    void that_convert_returns_available_result() {
        DroneStatusDto dto = DroneStatusDto.builder().success(true).build();
        DroneStatusResponse converted = usecase.convert(dto);
        assertEquals(DroneStatusResponse.Result.AVAILABLE, converted.result());
    }
}