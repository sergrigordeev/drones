package com.musala.sg.drones.domain.usecases.exception;

import com.musala.sg.drones.domain.usecases.api.Request;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.check.CheckCargoQuery;
import lombok.Getter;

@Getter
public class NoDroneFoundException extends UsecaseException {
    private final Request request;

    public NoDroneFoundException(CheckCargoQuery query) {
        super("No drone with SN %s has been found".formatted(query.serialNumber()));
        this.request = query;
    }
}
