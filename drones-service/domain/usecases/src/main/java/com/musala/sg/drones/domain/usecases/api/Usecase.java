package com.musala.sg.drones.domain.usecases.api;

public interface Usecase<P extends Request, A extends Response> {

    A execute(P request);
}
