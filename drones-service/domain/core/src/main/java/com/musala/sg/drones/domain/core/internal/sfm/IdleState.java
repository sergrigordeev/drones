package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;


public class IdleState extends DroneFSM {
    public IdleState(DroneImpl drone) {
        super(drone, State.IDLE);
    }

    @Override
    public void idle() {
        getDrone().updateState(this);
    }

    @Override
    public void startLoading() {
        getDrone().updateState(new LoadingState(getDrone()));
    }

    @Override
    public void returnToBase() {
        getDrone().updateState(new ReturningState(getDrone()));
    }

    @Override
    public void startCharging() {
        getDrone().updateState(new ChargingState(getDrone()));
    }


}
