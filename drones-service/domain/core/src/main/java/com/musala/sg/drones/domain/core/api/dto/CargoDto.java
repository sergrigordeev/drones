package com.musala.sg.drones.domain.core.api.dto;

import com.musala.sg.drones.domain.core.api.Cargo;
import lombok.Value;

@Value
public class CargoDto implements Cargo {
    private final String name;
    private final String code;
    private final int weight;
    private final String imageUrl;
}
