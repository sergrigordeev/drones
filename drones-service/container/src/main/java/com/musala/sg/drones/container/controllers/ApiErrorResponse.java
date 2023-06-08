package com.musala.sg.drones.container.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(int errorCode, String message, Map<String, List<String>> fieldsErrors) {
    public static ApiErrorResponse of500(String msg) {
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
    }

    public static ApiErrorResponse of404(Exception ex) {
        return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
    }

    public static ApiErrorResponse of400(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            String field = e.getField();
            errors.putIfAbsent(field, new ArrayList<>());
            errors.get(field).add(e.getDefaultMessage());
        });

        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "validation failed", errors);
    }

    public static ApiErrorResponse of409(Exception ex) {
        return new ApiErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
    }

    public static ApiErrorResponse of400(Exception ex) {
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }
}
