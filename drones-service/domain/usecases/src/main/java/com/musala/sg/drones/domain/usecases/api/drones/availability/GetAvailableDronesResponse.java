package com.musala.sg.drones.domain.usecases.api.drones.availability;

import com.musala.sg.drones.domain.usecases.api.DroneResponse;
import com.musala.sg.drones.domain.usecases.api.Response;

import java.util.List;

public record GetAvailableDronesResponse(List<DroneResponse> drones) implements Response {


}
