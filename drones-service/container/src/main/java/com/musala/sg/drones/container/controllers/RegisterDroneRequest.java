package com.musala.sg.drones.container.controllers;

import com.musala.sg.drones.domain.usecases.api.drones.registration.RegisterDroneCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class RegisterDroneRequest {
    @Length(min = 1, max = 200)
    private String serialNumber;
    @NotBlank
    private String model;
    @Range(min = 1, max = 500)
    private int maxWeight;

    public RegisterDroneCommand toCommand() {
        return new RegisterDroneCommand(serialNumber, model, maxWeight);
    }
}
