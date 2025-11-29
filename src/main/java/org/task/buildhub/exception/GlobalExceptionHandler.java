package org.task.buildhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        System.err.println("RUNTIME EXCEPTION: " + ex.getClass().getSimpleName());

        Map<String, String> response = new HashMap<>();
        response.put("error", getSafeMessage(ex));
        response.put("type", ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        System.err.println("GENERAL EXCEPTION: " + ex.getClass().getName());
        ex.printStackTrace();

        Map<String, String> response = new HashMap<>();
        response.put("error", getSafeMessage(ex));
        response.put("type", ex.getClass().getSimpleName());
        response.put("message", "Internal server error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    private String getSafeMessage(Exception ex) {
        try {
            return ex.getMessage();
        } catch (Exception e) {
            return "Error message could not be retrieved: " + e.getClass().getSimpleName();
        }
    }
}