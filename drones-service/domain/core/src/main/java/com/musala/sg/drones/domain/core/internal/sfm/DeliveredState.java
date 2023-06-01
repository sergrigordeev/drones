package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;

public class DeliveredState extends DroneFSM {
    public DeliveredState(DroneImpl drone) {
        super(drone, State.DELIVERED);
    }
}
