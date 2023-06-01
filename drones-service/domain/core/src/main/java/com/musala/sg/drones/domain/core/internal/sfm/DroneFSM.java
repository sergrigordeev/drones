package com.musala.sg.drones.domain.core.internal.sfm;

import com.musala.sg.drones.domain.core.api.Medication;
import com.musala.sg.drones.domain.core.api.State;
import com.musala.sg.drones.domain.core.internal.DroneImpl;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class DroneFSM {

    private final DroneImpl drone;
    private final State state;

    DroneFSM(@NonNull DroneImpl drone, @NonNull State state) {
        this.drone = drone;
        this.state = state;
    }

    public static DroneFSM of(DroneImpl drone, State state) {
        return switch (state) {
            case IDLE -> new IdleState(drone);
            case LOADING -> new LoadingState(drone);
            case LOADED -> new LoadedState(drone);
            case DELIVERING -> new DeliveringState(drone);
            case DELIVERED -> new DeliveredState(drone);
            case UNLOADING -> new UnloadingState(drone);
            case UNLOADED -> new UnloadedState(drone);
            case RETURNING -> new ReturningState(drone);
        };
    }

    public void startLoading() {
        throw new UnsupportedOperationException("Start load is unsupported for %s".formatted(state));
    }

    public void load(Medication medication) {
        throw new UnsupportedOperationException("Load is unsupported for %s".formatted(state));
    }

    public void endLoading() {
        throw new UnsupportedOperationException("End load is unsupported for %s".formatted(state));
    }

    public void startDelivery() {
        throw new UnsupportedOperationException("Start Delivery is unsupported for %s".formatted(state));
    }

    public void delivered() {
        throw new UnsupportedOperationException("Start Delivery is unsupported for %s".formatted(state));
    }


    public void startUnloading() {
        throw new UnsupportedOperationException("Start unload is unsupported for %s".formatted(state));
    }

    public void unloadAll() {
        throw new UnsupportedOperationException("Unload All is unsupported for %s".formatted(state));
    }

    public void endUnloading() {
        throw new UnsupportedOperationException("End load is unsupported for %s".formatted(state));
    }

    public void returnToBase() {
        throw new UnsupportedOperationException("Return to base is unsupported for %s".formatted(state));
    }

    public void idle() {
        throw new UnsupportedOperationException("Idle to base is unsupported for %s".formatted(state));
    }

    public void charge() {
        throw new UnsupportedOperationException("Charge to base is unsupported for %s".formatted(state));
    }
}
