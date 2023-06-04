package com.musala.sg.drones.domain.usecases.exception;

import com.musala.sg.drones.domain.usecases.api.Request;
import com.musala.sg.drones.domain.usecases.api.drones.battery.GetDroneBatteryLevelQuery;
import lombok.Getter;

@Getter
public class BatteryLevelNotFoundException extends UsecaseException{
    private final Request request;
    public BatteryLevelNotFoundException(GetDroneBatteryLevelQuery request) {
        super("No battery level log found for drone with sn %s".formatted(request.serialNumber()));
        this.request = request;
    }
}
