package com.musala.sg.drones.domain.usecases.api.ports;

import com.musala.sg.drones.domain.core.api.DroneDto;
import com.musala.sg.drones.domain.usecases.api.drones.availability.GetAvailableDronesQuery;

import java.util.List;

public interface FindDronesPort {
    
    List<DroneDto> findAllAvailableDrones(GetAvailableDronesQuery query);
}
