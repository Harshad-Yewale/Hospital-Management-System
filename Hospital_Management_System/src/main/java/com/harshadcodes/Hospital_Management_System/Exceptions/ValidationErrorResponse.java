package com.harshadcodes.Hospital_Management_System.Exceptions;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        List<FieldErrorResponse> errors
) {}
