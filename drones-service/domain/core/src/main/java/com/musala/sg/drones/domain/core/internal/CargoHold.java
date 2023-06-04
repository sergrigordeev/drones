package com.musala.sg.drones.domain.core.internal;

import com.musala.sg.drones.domain.core.api.Cargo;
import com.musala.sg.drones.domain.core.api.exceptions.CargoLoadException;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CargoHold {
    private static final int MAX_ALLOWED_WEIGHT = 500;
    private final int maxWeight;
    private List<Cargo> cargos;

    public CargoHold(int maxWeight) {
        this(maxWeight, new ArrayList<>());
    }

    public CargoHold(int maxWeight, @NonNull List<Cargo> cargos) {
        this.maxWeight = maxWeight;
        this.cargos = cargos;
        selfCheck();
    }

    private void selfCheck() {
        if (!isMaxWeightCorrect()) {
            throw new IllegalStateException("Max weight can not be less than 0, but it is " + maxWeight);
        }
        if (!isTotalWeightCorrect()) {
            throw new IllegalStateException("Total weight of cargos is more than max weight.Max %s; Total %s".formatted(maxWeight, totalWeight()));
        }
    }

    private boolean isTotalWeightCorrect() {
        return maxWeight >= totalWeight();
    }

    private boolean isMaxWeightCorrect() {
        return maxWeight >= 0 && maxWeight <= MAX_ALLOWED_WEIGHT;
    }

    void load(@NonNull Cargo cargo) {
        if (availableWeight() - cargo.getWeight() < 0) {
            throw new CargoLoadException("A cargo to heavy. max %s, available %s, cargo weight %s".formatted(maxWeight, availableWeight(), cargo.getWeight()));
        }
        cargos.add(cargo);
    }

    void unloadAll() {
        cargos = new ArrayList<>();
    }

    int totalCargo() {
        return cargos.size();
    }

    int totalWeight() {
        return cargos.stream().reduce(0, (sum, cargo) -> sum + cargo.getWeight(), Integer::sum);
    }

    int availableWeight() {
        return maxWeight - totalWeight();
    }
}
