package com.musala.sg.drones.adapters.jpa.drone.api;

import com.musala.sg.drones.adapters.jpa.drone.api.DroneAdapter;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaCargo;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaCargoRepository;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDrone;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDroneRepository;
import com.musala.sg.drones.adapters.jpa.drone.internal.mappers.JpaCargoMapper;
import com.musala.sg.drones.adapters.jpa.drone.internal.mappers.JpaDroneMapper;
import com.musala.sg.drones.domain.core.api.dto.CargoDto;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.availability.GetAvailableDronesQuery;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.load.LoadMedicationCommand;
import com.musala.sg.drones.domain.usecases.api.ports.DronesBatteryLevelPort;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DroneAdapterTest {
    @InjectMocks
    DroneAdapter adapter;
    @Mock
    JpaDroneRepository mockJpaDroneRepo;
    @Mock
    JpaCargoRepository mockJpaCargoRepo;
    @Mock
    DronesBatteryLevelPort mockDronesBatteryLevelPort;
    @Spy
    JpaDroneMapper jpaDroneMapper = Mappers.getMapper(JpaDroneMapper.class);
    @Spy
    JpaCargoMapper jpaCargoMapper = Mappers.getMapper(JpaCargoMapper.class);

    @Nested
    class FindAllByState {
        @Test
        void that_returns_empty_list_when_no_available_drones() {
            GetAvailableDronesQuery query = new GetAvailableDronesQuery();

            DroneAdapter spyAdapter = spy(adapter);
            List<DroneDto> drones = spyAdapter.findAllAvailableDrones(query);

            assertTrue(drones.isEmpty());
            verify(mockJpaDroneRepo).findAllByState("IDLE");
            verify(spyAdapter, never()).toDtoWithBatteryLevel(any());
        }

        @Test
        void that_calls_all_subroutines_when_drones_are_available() {
            GetAvailableDronesQuery query = new GetAvailableDronesQuery();

            doReturn(List.of(mockJpaDrone(), mockJpaDrone())).when(mockJpaDroneRepo).findAllByState("IDLE");

            DroneAdapter spyAdapter = spy(adapter);
            List<DroneDto> drones = spyAdapter.findAllAvailableDrones(query);

            assertEquals(2, drones.size());
            verify(mockJpaDroneRepo).findAllByState("IDLE");
            verify(spyAdapter, times(2)).toDtoWithBatteryLevel(any());
        }
    }


    @Nested
    class FindByTest {
        @Test
        void that_returns_optional_empty_when_drone_doesnt_exists() {
            DroneSearchQuery query = new DroneSearchQuery("SN");

            JpaDrone mockJpaDrone = mockJpaDrone();


            doReturn(Optional.empty()).when(mockJpaDroneRepo).findBySerialNumber("SN");

            DroneAdapter spyAdapter = spy(adapter);
            Optional<DroneDto> optResult = spyAdapter.findBy(query);

            assertTrue(optResult.isEmpty());
            verify(mockJpaDroneRepo).findBySerialNumber("SN");
            verify(spyAdapter, never()).toDtoWithBatteryLevel(mockJpaDrone);
        }

        @Test
        void that_calls_all_subroutines_when_drone_exists() {
            DroneSearchQuery query = new DroneSearchQuery("SN");

            JpaDrone mockJpaDrone = mockJpaDrone();


            doReturn(Optional.of(mockJpaDrone)).when(mockJpaDroneRepo).findBySerialNumber("SN");

            DroneAdapter spyAdapter = spy(adapter);
            Optional<DroneDto> optResult = spyAdapter.findBy(query);

            assertTrue(optResult.isPresent());
            verify(mockJpaDroneRepo).findBySerialNumber("SN");
            verify(spyAdapter).toDtoWithBatteryLevel(mockJpaDrone);
        }
    }


    @Nested
    class BatteryLevelRequestTest {
        @Test
        void that_toDtoWithBatteryLevel_returns_dto_with_battery_lvl() {
            JpaDrone jpaDrone = mockJpaDrone();

            BatteryLevelLogDto batteryLog = new BatteryLevelLogDto("SN", 99, OffsetDateTime.MIN);
            doReturn(Optional.of(batteryLog)).when(mockDronesBatteryLevelPort).findLatestBySerialNumber(new DroneSearchQuery("SN"));
            DroneDto dto = adapter.toDtoWithBatteryLevel(jpaDrone);

            assertEquals("SN", dto.getSerialNumber());
            assertEquals(99, dto.getBatteryLevel());
        }

        @Test
        void that_latestBatteryLvl_returns_0_when_drone_battery_log_are_unavalable() {
            DroneSearchQuery query = new DroneSearchQuery("SN");
            doReturn(Optional.empty()).when(mockDronesBatteryLevelPort).findLatestBySerialNumber(query);
            int batteryLvl = adapter.latestBatteryLvl(query);

            assertEquals(0, batteryLvl);
        }

        @Test
        void that_latestBatteryLvl_returns_value_when_drone_stats_are_avalable() {
            DroneSearchQuery query = new DroneSearchQuery("SN");
            BatteryLevelLogDto batteryLog = new BatteryLevelLogDto("SN", 99, OffsetDateTime.MIN);
            doReturn(Optional.of(batteryLog)).when(mockDronesBatteryLevelPort).findLatestBySerialNumber(query);
            int batteryLvl = adapter.latestBatteryLvl(query);

            assertEquals(99, batteryLvl);
        }
    }

    @Nested
    class SaveTest {
        @Test
        void that_new_drone_saved_to_db() {

            DroneDto droneDto = DroneDto.builder()
                    .serialNumber("SN")
                    .model("MODEL")
                    .build();
            adapter.save(droneDto);

            verify(mockJpaDroneRepo).findBySerialNumber("SN");
            verify(mockJpaDroneRepo).save(argThat(e -> e.getId() == null && "SN".equals(e.getSerialNumber())));

        }

        @Test
        void that_the_drone_updated() {
            DroneDto droneDto = DroneDto.builder()
                    .serialNumber("SN")
                    .model("MODEL")
                    .build();
            JpaDrone existedDrone = mockJpaDrone();
            doReturn(Optional.of(existedDrone)).when(mockJpaDroneRepo).findBySerialNumber("SN");
            adapter.save(droneDto);

            verify(mockJpaDroneRepo).findBySerialNumber("SN");
            verify(mockJpaDroneRepo).save(existedDrone);

        }
    }

    @Nested
    class LoadMedicationTest {
        @Test
        void that_throws_when_no_drone_found() {
            LoadMedicationCommand command = new LoadMedicationCommand("SN", mockCargo());
            IllegalArgumentException exceptions = assertThrows(IllegalArgumentException.class, () -> adapter.loadMedication(command));

            assertEquals("No drone with SN SN exists", exceptions.getMessage());
        }

        @Test
        void that_add_cargo() {
            LoadMedicationCommand command = new LoadMedicationCommand("SN", mockCargo());
            JpaDrone mockDrone = mockJpaDrone();
            doReturn(Optional.of(mockDrone)).when(mockJpaDroneRepo).findBySerialNumber("SN");
            adapter.loadMedication(command);

            verify(mockJpaDroneRepo).findBySerialNumber("SN");
            verify(jpaCargoMapper).partialUpdate(eq(command.medication()), argThat(c -> c.getDrone().getSerialNumber().equals("SN")));
            verify(mockJpaCargoRepo).save(any(JpaCargo.class));
        }

        private CargoDto mockCargo() {

            return new CargoDto("name", "CODE", 1, "img");
        }
    }


    protected static JpaDrone mockJpaDrone() {
        JpaDrone jpaDrone = new JpaDrone();
        jpaDrone.setId(UUID.randomUUID());
        jpaDrone.setSerialNumber("SN");
        return jpaDrone;
    }
}