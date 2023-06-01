package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class LoadedState extends DroneFSM {
    public LoadedState(DroneImpl drone) {
        super(drone, State.LOADED);
    }
}
