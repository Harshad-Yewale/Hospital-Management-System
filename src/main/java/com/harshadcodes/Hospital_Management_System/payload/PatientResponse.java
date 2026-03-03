package com.harshadcodes.Hospital_Management_System.payload;


import java.time.LocalDate;
import java.time.LocalDateTime;


public record PatientResponse(
        Long id,
        String patientName,
        String email,
        String gender,
        LocalDate birthDate,
        String bloodGroup,
        Long InsuranceId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
