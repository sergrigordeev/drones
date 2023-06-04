package com.musala.sg.drones.domain.core.internal;

import lombok.Getter;

@Getter
public class Battery {
    private int batteryLevel;

    public Battery(int batteryLevel) {
        this.batteryLevel = batteryLevel;
        selfCheck();
    }

    private void selfCheck() {
        if (!isBatteryCapacityCorrect()) {
            throw new IllegalStateException("The battery capacity should be in range 1-100, but it is " + batteryLevel);
        }
    }

    private boolean isBatteryCapacityCorrect() {
        return batteryLevel >= 0 && batteryLevel <= 100;
    }

    public void updateBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
        selfCheck();
    }
}
