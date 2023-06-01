package com.musala.sg.drones.domain.core.api;

public interface Drone {
    void load(Medication medication);

    void upload();

    Integer getBatteryLevel();

    boolean couldBeLoaded();
}
