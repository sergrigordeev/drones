package com.musala.sg.drones.container.controllers;

import com.musala.sg.drones.domain.core.api.dto.CargoDto;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.load.LoadMedicationCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class LoadMedicationRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String code;
    @Min(1)
    private int weight;
    @NotBlank
    private String imageUrl;

    public LoadMedicationCommand toCommand(@NonNull String serialNumber) {
        return new LoadMedicationCommand(serialNumber, new CargoDto(name, code, weight, imageUrl));
    }
}
