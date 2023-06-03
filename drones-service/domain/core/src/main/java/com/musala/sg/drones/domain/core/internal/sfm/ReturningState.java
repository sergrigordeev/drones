package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class ReturningState extends DroneFSM {
    public ReturningState(DroneImpl drone) {
        super(drone, State.RETURNING);
    }

    @Override
    public void idle() {
        getDrone().idle();
        getDrone().updateState(new IdleState(getDrone()));
    }

    @Override
    public void returnToBase() {
        getDrone().returnToBase();
        getDrone().updateState(this);
    }
}
