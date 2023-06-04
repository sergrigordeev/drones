package com.musala.sg.drones.domain.core.api;

import java.util.List;

public interface Drone {

    void startLoading();

    void load(Medication medication);

    void endLoading();

    void startDelivery();

    void startUnloading();

    void unloadAll();

    void returnToBase();

    void startCharging();

    int getBatteryLevel();

    List<Cargo> getCargos();
}
