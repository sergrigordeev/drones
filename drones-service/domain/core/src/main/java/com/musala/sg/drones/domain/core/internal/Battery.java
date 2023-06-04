package com.musala.sg.drones.domain.core.internal;

import lombok.Getter;

@Getter
public class Battery {
    public static final int MAX_BATTERY_LEVEL = 100;
    public static final int MIN_BATTERY_LEVEL = 0;
    private int batteryLevel;

    public Battery(int batteryLevel) {
        this.batteryLevel = batteryLevel;
        selfCheck();
    }

    public static Battery full() {
        return new Battery(MAX_BATTERY_LEVEL);
    }

    private void selfCheck() {
        if (!isBatteryCapacityCorrect()) {
            throw new IllegalStateException("The battery capacity should be in range 1-100, but it is " + batteryLevel);
        }
    }

    private boolean isBatteryCapacityCorrect() {
        return batteryLevel >= MIN_BATTERY_LEVEL && batteryLevel <= MAX_BATTERY_LEVEL;
    }

    public void updateBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
        selfCheck();
    }
}
