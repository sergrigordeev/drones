package com.musala.sg.drones.domain.core.internal;

import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.sfm.DroneFSM;

import java.util.Objects;


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

    public DroneImpl(String serialNumber, String model, int maxWeight, int batteryCapacity, State state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.maxWeight = maxWeight;
        this.batteryCapacity = batteryCapacity;
        this.fsm = DroneFSM.of(this, state);
    }

    public DroneFSM getFSM() {
        return fsm;
    }

    public void updateState(DroneFSM state) {
        this.fsm = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroneImpl drone = (DroneImpl) o;
        return serialNumber.equals(drone.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }

    public void load(Medication medication) {

    }

    public void startDelivery() {

    }

    public void endDelivery() {

    }

    public void startUnloading() {

    }

    public void unloadAll() {
    }

    public void returnToBase() {

    }

    public void startCharging() {

    }
}
