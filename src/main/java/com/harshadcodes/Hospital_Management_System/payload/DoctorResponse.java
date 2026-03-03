package com.harshadcodes.Hospital_Management_System.payload;

import java.time.LocalDateTime;

public record DoctorResponse(
        Long id,
        String doctorName,
        String email,
        String specialization,
        Double salary,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}