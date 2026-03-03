package com.harshadcodes.Hospital_Management_System.controllers;


import com.harshadcodes.Hospital_Management_System.payload.DoctorCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.DoctorResponse;
import com.harshadcodes.Hospital_Management_System.services.DepartmentService;
import com.harshadcodes.Hospital_Management_System.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    // CREATE
    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(
            @Valid @RequestBody DoctorCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.createDoctor(request));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                doctorService.getDoctorById(id)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {

        return ResponseEntity.ok(
                doctorService.getAllDoctors()
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorCreateRequest request) {

        return ResponseEntity.ok(
                doctorService.updateDoctor(id, request)
        );
    }

    @GetMapping("/{id}/doctors")
    public List<DoctorResponse> getDoctors(@PathVariable Long id) {
        return departmentService.getDoctorsByDepartment(id);
    }



    // DELETE WITH REASSIGNMENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(
            @PathVariable Long id,
            @RequestParam Long replacementDoctorId) {

        doctorService.deleteDoctor(id, replacementDoctorId);
        return ResponseEntity.noContent().build();
    }
}