package com.musala.sg.drones.domain.core.internal;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@EqualsAndHashCode
@Value
public class DroneIdentity {

    public static final int MAX_SERIAL_NUM_LENGTH = 100;

    //TODO "hide" it. Use  factory
    public DroneIdentity(@NonNull String serialNumber, @NonNull Model model) {
        this.serialNumber = serialNumber;
        this.model = model;
        selfCheck();
    }

    private void selfCheck() {
        if (!isSerialNumberCorrect()) {
            throw new IllegalStateException("The serial number should has 1-100 chars, but it has " + serialNumber.length());
        }
    }

    private boolean isSerialNumberCorrect() {
        return !serialNumber.isBlank() && serialNumber.length() <= MAX_SERIAL_NUM_LENGTH;
    }

    private final String serialNumber;
    private final Model model;

    public enum Model {
        LIGHTWEIGHT, MIDDLEWEIGHT, CRUISERWEIGHT, HEAVYWEIGHT
    }

}
