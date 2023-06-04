package com.musala.sg.drones.domain.usecases.api.drones.cargo.check;

import com.musala.sg.drones.domain.usecases.api.Response;

public record MedicationResponse(String name, String code, int weight, String imgUrl) implements Response {
}
