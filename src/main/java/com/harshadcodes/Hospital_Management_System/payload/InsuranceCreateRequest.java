package com.harshadcodes.Hospital_Management_System.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InsuranceCreateRequest(
        @NotBlank String policyNumber,
        @NotBlank String provider,
        @NotNull LocalDate validUntil
) {}
