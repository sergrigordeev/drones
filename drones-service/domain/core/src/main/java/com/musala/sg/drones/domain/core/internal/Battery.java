package com.musala.sg.drones.domain.core.internal;

import lombok.Value;

@Value
public class Battery {
    public Battery(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        selfCheck();
    }

    private void selfCheck() {
        if (!isBatteryCapacityCorrect()) {
            throw new IllegalStateException("The battery capacity should be in range 1-100, but it is " + batteryCapacity);
        }
    }

    private boolean isBatteryCapacityCorrect() {
        return batteryCapacity >= 0 && batteryCapacity <= 100;
    }

    private final int batteryCapacity;
}
