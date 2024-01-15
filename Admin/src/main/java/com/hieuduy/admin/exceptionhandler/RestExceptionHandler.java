package com.hieuduy.admin.exceptionhandler;

import com.hieuduy.core.exception.ApiError;
import com.hieuduy.core.exception.DuplicateName;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = DuplicateName.class)
    public ResponseEntity<ApiError> handleDuplicateNameError(DuplicateName duplicateName) {
        ApiError apiError = new ApiError(400, duplicateName.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex){
        HashMap<String, String> error = new HashMap<>();
        ex.getConstraintViolations().stream().forEach(err -> {
            error.put(err.getPropertyPath().toString(), err.getMessage());
        });
        return error;
    }
}
