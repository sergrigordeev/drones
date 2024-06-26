package com.musala.sg.drones.domain.usecases.api.ports;

import com.musala.sg.drones.domain.core.api.dto.DroneDto;
import com.musala.sg.drones.domain.usecases.api.drones.availability.GetAvailableDronesQuery;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;

import java.util.List;
import java.util.Optional;

public interface FindDronesPort {
    
    List<DroneDto> findAllAvailableDrones(GetAvailableDronesQuery query);

    Optional<DroneDto> findBy(DroneSearchQuery query);

    List<DroneDto> findAll();
}
