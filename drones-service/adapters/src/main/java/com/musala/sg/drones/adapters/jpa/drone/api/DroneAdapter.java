package com.musala.sg.drones.adapters.jpa.drone.api;

import com.musala.sg.drones.adapters.jpa.drone.internal.JpaCargo;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaCargoRepository;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDrone;
import com.musala.sg.drones.adapters.jpa.drone.internal.JpaDroneRepository;
import com.musala.sg.drones.adapters.jpa.drone.internal.mappers.JpaCargoMapper;
import com.musala.sg.drones.adapters.jpa.drone.internal.mappers.JpaDroneMapper;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.availability.GetAvailableDronesQuery;
import com.musala.sg.drones.domain.usecases.api.drones.battery.BatteryLevelLogDto;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.load.LoadMedicationCommand;
import com.musala.sg.drones.domain.usecases.api.ports.DronesBatteryLevelPort;
import com.musala.sg.drones.domain.usecases.api.ports.FindDronesPort;
import com.musala.sg.drones.domain.usecases.api.ports.SaveDronePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
class DroneAdapter implements FindDronesPort, SaveDronePort {
    private final JpaDroneRepository droneRepository;
    private final JpaCargoRepository cargoRepository;
    private final DronesBatteryLevelPort dronesBatteryLevelPort;
    private final JpaDroneMapper jpaDroneMapper;
    private final JpaCargoMapper jpaCargoMapper;

    @Override
    public List<DroneDto> findAllAvailableDrones(GetAvailableDronesQuery query) {

        List<JpaDrone> drones = droneRepository.findAllByState("IDLE");
        return processCollection(drones);
    }

    @Override
    public List<DroneDto> findAll() {
        List<JpaDrone> drones = droneRepository.findAll();
        return processCollection(drones);
    }

    protected List<DroneDto> processCollection(List<JpaDrone> drones) {
        if (drones.isEmpty()) {
            return List.of();
        }
        List<DroneDto> dtos = new ArrayList<>(drones.size());
        for (JpaDrone drone : drones) {
            DroneDto dto = toDtoWithBatteryLevel(drone);
            dtos.add(dto);
        }
        return Collections.unmodifiableList(dtos);
    }


    @Override
    public Optional<DroneDto> findBy(DroneSearchQuery query) {
        Optional<JpaDrone> optDrone = droneRepository.findBySerialNumber(query.serialNumber());
        if (optDrone.isEmpty()) {
            return Optional.empty();
        }
        JpaDrone drone = optDrone.get();
        DroneDto dto = toDtoWithBatteryLevel(drone);
        return Optional.of(dto);
    }


    protected DroneDto toDtoWithBatteryLevel(JpaDrone drone) {
        int batteryLvl = latestBatteryLvl(new DroneSearchQuery(drone.getSerialNumber()));
        return jpaDroneMapper.toDto(drone, batteryLvl);
    }

    protected int latestBatteryLvl(DroneSearchQuery query) {
        Optional<BatteryLevelLogDto> latestBySerialNumber = dronesBatteryLevelPort.findLatestBySerialNumber(query);
        if (latestBySerialNumber.isEmpty()) {
            return 0;
        } else {
            return latestBySerialNumber.get().batteryLevel();
        }
    }

    @Override
    public void save(DroneDto droneDto) {
        Optional<JpaDrone> optJpaDrone = droneRepository.findBySerialNumber(droneDto.getSerialNumber());
        JpaDrone jpaDrone = optJpaDrone.orElseGet(JpaDrone::new);
        JpaDrone updated = jpaDroneMapper.partialUpdate(droneDto, jpaDrone);
        droneRepository.save(updated);
    }

    @Override
    public void loadMedication(LoadMedicationCommand command) {
        Optional<JpaDrone> optDrone = droneRepository.findBySerialNumber(command.serialNumber());
        if (optDrone.isEmpty()) {
            throw new IllegalArgumentException("No drone with SN %s exists".formatted(command.serialNumber()));
        }
        JpaCargo jpaCargo = new JpaCargo();
        jpaCargo.setDrone(optDrone.get());
        jpaCargoMapper.partialUpdate(command.medication(), jpaCargo);
        cargoRepository.save(jpaCargo);
    }
}
