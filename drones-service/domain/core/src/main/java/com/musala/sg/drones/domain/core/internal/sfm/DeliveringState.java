package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class DeliveringState extends DroneFSM {
    public DeliveringState(DroneImpl drone) {
        super(drone, State.DELIVERING);
    }

    @Override
    public void startDelivery() {
        getDrone().startDelivery();
        getDrone().updateState(this);
    }

    @Override
    public void endDelivery() {
        getDrone().endDelivery();
        getDrone().updateState(new DeliveredState(getDrone()));
    }
}
