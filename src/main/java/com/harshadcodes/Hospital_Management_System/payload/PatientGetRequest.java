package com.harshadcodes.Hospital_Management_System.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PatientGetRequest(
        @NotBlank(message = "Patient name is required")
        String patientName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
) { }
