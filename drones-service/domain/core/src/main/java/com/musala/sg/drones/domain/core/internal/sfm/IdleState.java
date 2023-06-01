package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;


public class IdleState extends DroneFSM {
    public IdleState(DroneImpl drone) {
        super(drone, State.IDLE);
    }

    @Override
    public void idle() {
        drone.updateState(this);
    }

    @Override
    public void startLoading() {
        drone.updateState(new LoadingState(getDrone()));
    }

    @Override
    public void returnToBase() {
        drone.updateState(new ReturningState(getDrone()));
    }

    @Override
    public void startCharging() {
        drone.updateState(new ChargingState(getDrone()));
    }


}
