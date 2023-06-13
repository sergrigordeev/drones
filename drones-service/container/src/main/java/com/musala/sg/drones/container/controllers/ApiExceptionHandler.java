package com.musala.sg.drones.container.controllers;

import com.musala.sg.drones.domain.core.api.exceptions.CargoLoadException;
import com.musala.sg.drones.domain.usecases.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {DroneNotFoundException.class, BatteryLevelNotFoundException.class})
    protected ResponseEntity<ApiErrorResponse> handleReservationException(UsecaseException ex) {
        logIt(ex);
        return new ResponseEntity<>(ApiErrorResponse.of404(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        logIt(ex);
        return new ResponseEntity<>(ApiErrorResponse.of400(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DroneRegistrationException.class, LoadMedicationException.class})
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(UsecaseException ex) {
        logIt(ex);
        return new ResponseEntity<>(ApiErrorResponse.of400(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ApiErrorResponse> fallback(RuntimeException ex) {
        logIt(ex);
        return new ResponseEntity<>(ApiErrorResponse.of500("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logIt(Throwable ex) {
        log.error("Runtime error: ", ex);
    }
}
