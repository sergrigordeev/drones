package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class ReturningState extends DroneFSM {
    public ReturningState(DroneImpl drone) {
        super(drone, State.RETURNING);
    }
}
