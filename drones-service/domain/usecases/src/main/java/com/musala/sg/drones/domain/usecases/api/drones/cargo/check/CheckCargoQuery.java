package com.musala.sg.drones.domain.usecases.api.drones.cargo.check;

import com.musala.sg.drones.domain.usecases.api.Query;

public record CheckCargoQuery(String serialNumber) implements Query {
}
