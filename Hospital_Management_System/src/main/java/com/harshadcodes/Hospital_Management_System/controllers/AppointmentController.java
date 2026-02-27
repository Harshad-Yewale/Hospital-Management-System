package com.harshadcodes.Hospital_Management_System.controllers;

import com.harshadcodes.Hospital_Management_System.payload.AppointmentRequest;
import com.harshadcodes.Hospital_Management_System.payload.AppointmentResponse;
import com.harshadcodes.Hospital_Management_System.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {


        private final AppointmentService appointmentService;

        @PostMapping
        public ResponseEntity<AppointmentResponse> create(
                @Valid @RequestBody AppointmentRequest request) {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(appointmentService.createAppointment(request));
        }

        @GetMapping("/patient/{patientId}")
        public List<AppointmentResponse> getByPatient(
                @PathVariable long patientId) {

            return appointmentService.getAllAppointments(patientId);
        }

        @PutMapping("/{id}")
        public AppointmentResponse update(
                @PathVariable long id,
                @Valid @RequestBody AppointmentRequest request) {

            return appointmentService.updateAppointment(id, request);
        }

        @PatchMapping("/{id}/cancel")
        public AppointmentResponse cancel(@PathVariable long id) {
            return appointmentService.deleteAppointment(id);
        }

        @PatchMapping("/{id}/complete")
        public AppointmentResponse complete(@PathVariable long id) {
            return appointmentService.completeAppointment(id);
        }

}
