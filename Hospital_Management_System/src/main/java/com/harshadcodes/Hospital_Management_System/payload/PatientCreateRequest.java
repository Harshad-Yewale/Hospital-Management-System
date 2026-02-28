package com.harshadcodes.Hospital_Management_System.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientCreateRequest(
        @NotBlank(message = "Patient name is required")
        String patientName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Gender is required")
        String gender,

        @NotNull(message = "BirthDate is required")
        LocalDate birthDate,

        @NotBlank(message = "BloodGroup is required")
        String bloodGroup
) {}
