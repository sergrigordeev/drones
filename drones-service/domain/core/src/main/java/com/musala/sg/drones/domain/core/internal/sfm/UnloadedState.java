package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class UnloadedState extends DroneFSM {
    public UnloadedState(DroneImpl drone) {
        super(drone, State.UNLOADED);
    }
}
