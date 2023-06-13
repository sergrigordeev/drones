package com.musala.sg.drones.container.controllers;

import com.musala.sg.drones.domain.usecases.api.DroneResponse;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.availability.GetAvailableDronesQuery;
import com.musala.sg.drones.domain.usecases.api.drones.availability.GetAvailableDronesResponse;
import com.musala.sg.drones.domain.usecases.api.drones.availability.GetAvailableDronesUsecase;
import com.musala.sg.drones.domain.usecases.api.drones.battery.GetDroneBatteryLevelResponse;
import com.musala.sg.drones.domain.usecases.api.drones.battery.GetDroneBatteryLevelUsecase;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.check.CheckCargoForDroneUsecase;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.check.CheckCargoQuery;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.check.CheckCargoResponse;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.load.LoadMedicationResponse;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.load.LoadMedicationUsecase;
import com.musala.sg.drones.domain.usecases.api.drones.registration.RegisterDroneUsecase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/drones")
@AllArgsConstructor
public class DroneController {

    private final RegisterDroneUsecase registerDroneUsecase;
    private final LoadMedicationUsecase loadMedicationUsecase;
    private final CheckCargoForDroneUsecase checkCargoForDroneUsecase;
    private final GetAvailableDronesUsecase getAvailableDronesUsecase;
    private final GetDroneBatteryLevelUsecase getDroneBatteryLevelUsecase;

    /**
     * The service should allow:
     * <p>
     * - check drone battery level for a given drone;
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    DroneResponse register(@Valid @RequestBody RegisterDroneRequest request) {
        return registerDroneUsecase.execute(request.toCommand());
    }

    @PutMapping(value = "/{serialNumber}/medication", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    LoadMedicationResponse load(@PathVariable("serialNumber") String serialNumber, @Valid @RequestBody LoadMedicationRequest request) {
        return loadMedicationUsecase.execute(request.toCommand(serialNumber));
    }

    @GetMapping(value = "/{serialNumber}/medication", produces = APPLICATION_JSON_VALUE)
    CheckCargoResponse checkLoadedMedication(@PathVariable("serialNumber") String serialNumber) {
        return checkCargoForDroneUsecase.execute(new CheckCargoQuery(serialNumber));
    }

    @GetMapping(value = "/{serialNumber}/battery", produces = APPLICATION_JSON_VALUE)
    GetDroneBatteryLevelResponse checkDroneBattery(@PathVariable("serialNumber") String serialNumber) {
        return getDroneBatteryLevelUsecase.execute(new DroneSearchQuery(serialNumber));
    }

    @GetMapping(value = "/available", produces = APPLICATION_JSON_VALUE)
    GetAvailableDronesResponse checkAvailableDrones() {
        return getAvailableDronesUsecase.execute(new GetAvailableDronesQuery());
    }
}
