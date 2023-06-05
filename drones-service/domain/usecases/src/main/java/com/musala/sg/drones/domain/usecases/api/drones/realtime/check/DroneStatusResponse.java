package com.musala.sg.drones.domain.usecases.api.drones.realtime.check;

import com.musala.sg.drones.domain.usecases.api.Response;

public record DroneStatusResponse(Result result) implements Response {

    enum Result{
        AVAILABLE, UNAVAILABLE;
    }
}
