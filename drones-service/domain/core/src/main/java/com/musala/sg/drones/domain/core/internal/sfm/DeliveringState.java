package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class DeliveringState extends DroneFSM {
    public DeliveringState(DroneImpl drone) {
        super(drone, State.DELIVERING);
    }
}
