package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class DeliveredState extends DroneFSM {
    public DeliveredState(DroneImpl drone) {
        super(drone, State.DELIVERED);
    }

    @Override
    public void endDelivery() {
        getDrone().endDelivery();
        getDrone().updateState(this);
    }

    @Override
    public void startUnloading() {
        getDrone().startUnloading();
        getDrone().updateState(new UnloadingState(getDrone()));
    }
}
