package com.harshadcodes.Hospital_Management_System.Exceptions;

public record FieldErrorResponse(
        String field,
        String message
) {
}
