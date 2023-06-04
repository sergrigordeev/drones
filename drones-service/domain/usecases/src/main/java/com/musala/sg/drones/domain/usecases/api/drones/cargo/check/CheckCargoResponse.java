package com.musala.sg.drones.domain.usecases.api.drones.cargo.check;

import com.musala.sg.drones.domain.usecases.api.Response;

import java.util.List;

public record CheckCargoResponse(int maxWeight, int available,
                                 List<MedicationResponse> medications) implements Response {
}
