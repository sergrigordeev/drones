package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.api.exceptions.LoadingStateException;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class LoadingState extends DroneFSM {
    private static final int BATTERY_LVL_THRESHOLD = 25;

    public LoadingState(DroneImpl drone) {
        super(drone, State.LOADING);
        int batteryLevel = drone.getBatteryLevel();
        if (BATTERY_LVL_THRESHOLD > batteryLevel) {
            throw new LoadingStateException("Battery Level is to low THRESHOLD is %s, but current is %s".formatted(BATTERY_LVL_THRESHOLD, batteryLevel));
        }
    }

    @Override
    public void startLoading() {
        getDrone().updateState(this);
    }

    @Override
    public void load(Medication medication) {
        getDrone().updateState(this);
        getDrone().loadToCargoHold(medication);
    }

    @Override
    public void endLoading() {
        getDrone().updateState(new LoadedState(getDrone()));
    }
}
