package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class UnloadingState extends DroneFSM {
    public UnloadingState(DroneImpl drone) {
        super(drone, State.UNLOADING);
    }

    @Override
    public void startUnloading() {
        getDrone().startUnloading();
        getDrone().updateState(this);
    }

    @Override
    public void unloadAll() {
        getDrone().unloadAll();
        getDrone().updateState(new UnloadedState(getDrone()));
    }
}
