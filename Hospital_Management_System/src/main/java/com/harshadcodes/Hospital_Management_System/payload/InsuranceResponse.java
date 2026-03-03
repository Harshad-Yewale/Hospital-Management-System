package com.harshadcodes.Hospital_Management_System.payload;

import java.time.LocalDate;
import java.util.List;

public record InsuranceResponse(
        Long id,
        String policyNumber,
        String provider,
        LocalDate validUntil,
        List<Long> patientIds
) {}
