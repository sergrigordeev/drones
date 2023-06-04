package com.musala.sg.drones.domain.core.internal;

import com.musala.sg.drones.domain.core.api.Cargo;
import com.musala.sg.drones.domain.core.api.Drone;
import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.sfm.DroneFSM;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;


/**
 * - serial number (100 characters max);
 * - model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
 * - weight limit (500gr max);
 * - battery capacity (percentage);
 * - state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).
 */

public class DroneImpl implements Drone {

    private final DroneIdentity identity;

    private final CargoHold cargoHold;
    private Battery battery;
    private DroneFSM fsm;

    public DroneImpl(@NonNull DroneIdentity identity, @NonNull CargoHold cargoHold, @NonNull Battery battery, @NonNull State state) {
        this.identity = identity;
        this.cargoHold = cargoHold;
        this.battery = battery;
        this.fsm = DroneFSM.of(this, state);
    }

    @Override
    public int getBatteryLevel() {
        return battery.getBatteryLevel();
    }

    @Override
    public List<Cargo> getCargos() {
        return cargoHold.getCargos();
    }

    @Override
    public int getMaxWeight() {
        return cargoHold.getMaxWeight();
    }

    @Override
    public int getAvailableWeight() {
        return cargoHold.availableWeight();
    }

    @Override
    public DroneIdentity getIdentity() {
        return identity;
    }

    @Override
    public void startLoading() {
        fsm.startLoading();
    }

    @Override
    public void load(Medication medication) {
        fsm.load(medication);
    }

    @Override
    public void endLoading() {
        fsm.endLoading();
    }

    @Override
    public void startDelivery() {
        fsm.startDelivery();
    }

    public void endDelivery() {
        fsm.endDelivery();
    }

    @Override
    public void startUnloading() {
        fsm.startUnloading();
    }

    @Override
    public void unloadAll() {
        fsm.unloadAll();
    }

    @Override
    public void returnToBase() {
        fsm.returnToBase();
    }

    @Override
    public void startCharging() {
        fsm.startCharging();
    }
    public void idle() {
        fsm.idle();
    }
    public void endCharging() {
        fsm.endCharging();
    }

    public boolean couldLoadCargo() {
        return cargoHold.availableWeight() > 0;
    }

    public void loadToCargoHold(Medication medication) {
        cargoHold.load(medication);
    }

    public void unloadAllFromCargo() {
        cargoHold.unloadAll();
    }

    public DroneFSM getFSM() {
        return fsm;
    }

    public void updateState(@NonNull DroneFSM state) {
        this.fsm = state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroneImpl drone = (DroneImpl) o;
        return identity.equals(drone.identity);
    }
}
