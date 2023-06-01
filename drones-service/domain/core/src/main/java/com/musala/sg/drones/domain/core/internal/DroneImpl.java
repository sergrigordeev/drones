package com.musala.sg.drones.domain.core.internal;

import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.sfm.DroneFSM;
import lombok.ToString;


/**
 * - serial number (100 characters max);
 * - model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
 * - weight limit (500gr max);
 * - battery capacity (percentage);
 * - state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).
 */

public class DroneImpl implements Drone {

    private final String serialNumber;
    private final String model;
    private final int maxWeight;
    private int batteryCapacity;
    private DroneFSM fsm;

    DroneImpl(String serialNumber, String model, int maxWeight, int batteryCapacity, State state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.maxWeight = maxWeight;
        this.batteryCapacity = batteryCapacity;
        this.fsm = DroneFSM.of(this, state);
    }

    @Override
    public void load(Medication medication){

    }

    @Override
    public void upload(){

    }

    @Override
    public Integer getBatteryLevel(){
        return 0;
    }

    @Override
    public boolean couldBeLoaded(){
        return false;
    }
}
