package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class LoadingState extends DroneFSM {
    public LoadingState(DroneImpl drone) {
        super(drone, State.LOADING);
    }

    @Override
    public void startLoading() {
       getDrone().updateState(this);
    }

    @Override
    public void load(Medication medication) {
        getDrone().updateState(this);
        getDrone().load(medication);
    }

    @Override
    public void endLoading() {
        getDrone().updateState(new LoadedState(getDrone()));
    }
}
