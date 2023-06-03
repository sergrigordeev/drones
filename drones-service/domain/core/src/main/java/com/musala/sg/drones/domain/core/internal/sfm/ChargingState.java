package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;


public class ChargingState extends DroneFSM {
    public ChargingState(DroneImpl drone) {
        super(drone, State.CHARGING);
    }

    @Override
    public void startCharging() {
        getDrone().startCharging();
        getDrone().updateState(this);
    }

    @Override
    public void endCharging() {
        getDrone().endCharging();
        getDrone().updateState(new IdleState(getDrone()));
    }
}
