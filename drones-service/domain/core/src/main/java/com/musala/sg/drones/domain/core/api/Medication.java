package com.musala.sg.drones.domain.core.api;

import lombok.Getter;
import lombok.NonNull;

/**
 * - name (allowed only letters, numbers, ‘-‘, ‘_’);
 * - weight;
 * - code (allowed only upper case letters, underscore and numbers);
 * - image (picture of the medication case).
 */
@Getter
public class Medication {

    private final String name;
    private final String code;
    private final int weight;
    private final String image;

    public Medication(@NonNull String name, @NonNull String code, int weight, @NonNull String image) {
        this.name = name;
        this.code = code;
        this.weight = weight;
        this.image = image;
        selfCheck();
    }

    private void selfCheck() {
        if (!isWeightCorrect()){
            throw new IllegalStateException("Weight should be more than 1g, but it is "+ weight);
        }
        if (!isNameCorrect()){
            throw new IllegalStateException("Name should contains only letters, '_', '-', but it is "+ name);
        }
        if (!isCodeCorrect()){
            throw new IllegalStateException("Code should contains only uppercase letters, '_', numbers, but it is "+ code);
        }
    }

    private boolean isWeightCorrect() {
        return weight > 0;
    }

    private boolean isNameCorrect() {
        return name.matches("[a-zA-Z\\-_]+");
    }
    private boolean isCodeCorrect() {
        return code.matches("[A-Z0-9_]+");
    }
}
