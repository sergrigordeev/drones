package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class LoadingState extends DroneFSM {
    public LoadingState(DroneImpl drone) {
        super(drone, State.LOADING);
    }
}
