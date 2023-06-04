package com.musala.sg.drones.domain.core.internal;

import com.musala.sg.drones.domain.core.api.*;
import com.musala.sg.drones.domain.core.api.dto.DroneDto;

public class DroneFactoryImpl implements DroneFactory {
    @Override
    public Drone restore(DroneDto droneDto) {
        DroneIdentity identity = getIdentity(droneDto);
        CargoHold cargoHold = getCargoHold(droneDto);
        Battery battery = getBattery(droneDto);
        return new DroneImpl(identity, cargoHold, battery, State.valueOf(droneDto.getState()));
    }


    protected static DroneIdentity getIdentity(DroneDto droneDto) {
        return new DroneIdentity(droneDto.getSerialNumber(), DroneIdentity.Model.valueOf(droneDto.getModel()));
    }

    protected static CargoHold getCargoHold(DroneDto droneDto) {
        CargoHold cargoHold = new CargoHold(droneDto.getMaxWeight());
        if (droneDto.getCargos() != null) {
            for (Cargo dto : droneDto.getCargos()) {
                Medication medication = new Medication(dto.getName(), dto.getCode(), dto.getWeight(), dto.getImageUrl());
                cargoHold.load(medication);
            }
        }
        return cargoHold;
    }

    protected static Battery getBattery(DroneDto droneDto) {
        return new Battery(droneDto.getBatteryLevel());
    }
}
