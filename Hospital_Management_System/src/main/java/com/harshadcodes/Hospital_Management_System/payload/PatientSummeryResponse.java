package com.harshadcodes.Hospital_Management_System.payload;



public record PatientSummeryResponse(
        Long id,
        String patientName,
        String Email,
        String gender
) {
}
