package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class IdleState extends DroneFSM {
    public IdleState(DroneImpl drone) {
        super(drone, State.IDLE);
    }
}
