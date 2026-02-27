package com.harshadcodes.Hospital_Management_System.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harshadcodes.Hospital_Management_System.entities.Appointment;

import java.time.LocalDateTime;
import java.util.List;


public record AppointmentResponse(
        Long id,
        String patientName,
        String doctorName,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime appointmentTime,
        String status



) {}
