package com.harshadcodes.Hospital_Management_System.payload;

public record DoctorCreateRequest(
        String doctorName,
        String email,
        String specialization,
        Double salary
) {}