package com.musala.sg.drones.domain.usecases.exception;

import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import lombok.Getter;

@Getter
public class NoDroneFoundException extends UsecaseException {
    private final DroneSearchQuery request;

    public NoDroneFoundException(DroneSearchQuery query) {
        super("No drone with SN %s has been found".formatted(query.serialNumber()));
        this.request = query;
    }
}
