package com.harshadcodes.Hospital_Management_System.controllers;

import com.harshadcodes.Hospital_Management_System.payload.PatientCreateRequest;
import com.harshadcodes.Hospital_Management_System.payload.PatientResponse;
import com.harshadcodes.Hospital_Management_System.payload.PatientSummeryResponse;
import com.harshadcodes.Hospital_Management_System.services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(request));
    }

    // GET BY ID
    @GetMapping("/{email}")
    public ResponseEntity<PatientSummeryResponse> getPatientById(@PathVariable String email) {

        return ResponseEntity.ok(patientService.getPatientByEmail(email));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {

        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PatchMapping("/{id}/insurance/{insuranceId}")
    public PatientResponse assignInsurance(
            @PathVariable Long id,
            @PathVariable Long insuranceId) {

        return patientService.assignInsurance(id, insuranceId);
    }

    @PatchMapping("/{id}/insurance/remove")
    public PatientResponse removeInsurance(@PathVariable Long id) {
        return patientService.removeInsurance(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id,
            @Valid @RequestBody PatientCreateRequest request) {

        return ResponseEntity.ok(patientService.updatePatient(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {

        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
